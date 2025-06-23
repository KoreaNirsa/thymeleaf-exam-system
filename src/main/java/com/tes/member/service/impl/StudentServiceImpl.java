package com.tes.member.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tes.member.domain.entity.Member;
import com.tes.member.domain.repository.StudentRepository;
import com.tes.member.model.request.StudentAddReqDTO;
import com.tes.member.model.response.StudentListResDTO;
import com.tes.member.service.StudentService;

import lombok.RequiredArgsConstructor;

/**
 * {@link StudentService}의 구현체로, 학생 목록 조회 및 페이징 처리를 담당합니다.
 * <p>
 * 학생 정보에는 이름, 기수, 연락처, 평균 점수, 등수가 포함되며,
 * 점수는 student_exam_submission 테이블의 평균을 기준으로 계산됩니다.
 * </p>
 *
 * <p>
 * 내부적으로 {@code nativeQuery} 기반 SQL을 사용하여 집계(AVG)와 페이징을 함께 처리하며,
 * Java 코드에서 등수(rank)는 {@link AtomicInteger}를 활용해 직접 계산합니다.
 * </p>
 *
 * @author 김재섭
 * @since 2025-06-04
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	private final StudentRepository studentRepository;
	private final PasswordEncoder passwordEncoder;
	
    /**
     * 기수별로 학생 목록을 조회하고, 각 기수 내에서 평균 점수 기준 등수를 계산한 뒤,
     * 전체 기수는 최신순(내림차순), 각 기수 내에서는 점수 등수 기준으로 정렬하여 반환합니다.
     * 
	 * <p>
	 * 데이터 조회는 native SQL 쿼리로 수행되며, 결과는 Object 배열 형태로 반환되고,
	 * Java 로직을 통해 기수별 그룹화 및 등수 계산 후 {@link StudentListResDTO} 리스트로 변환됩니다.
	 * </p>
	 * 
     * @param pageable 페이지 번호, 크기 등 페이징 정보
     * @return 정렬 및 등수가 반영된 학생 리스트의 페이지 객체
     */
	@Override
	public Page<StudentListResDTO> getStudentPage(Pageable pageable) {
		// DB에서 학생 목록 + 평균 점수를 페이징 조회 (Object[]로 구성된 결과)
	    Page<Object[]> rawPage = studentRepository.findStudentListWithAvgScore(pageable);

	    // 조회된 데이터를 기수(generation)를 기준으로 그룹화 (Map<String, List<Object[]>> 형태)
	    Map<String, List<Object[]>> groupedByGeneration = rawPage.getContent().stream()
	        .collect(Collectors.groupingBy(row -> (String) row[1]));

	    // 최종 변환된 DTO 리스트를 담을 리스트
	    List<StudentListResDTO> dtoList = new ArrayList<>();

	    // 각 기수별로 등수를 부여
	    groupedByGeneration.forEach((generation, rows) -> {
	        AtomicInteger rank = new AtomicInteger(1); // 기수별 등수 1부터 시작
	        
	        // 각 학생 데이터를 DTO로 변환하면서 등수를 추가
	        rows.forEach(row -> {
	            dtoList.add(new StudentListResDTO(
	                ((Long) row[0]).longValue(),      // member_id
	                generation,                      // generation
	                (String) row[2],                 // name
	                (String) row[3],                 // phone
	                ((Double) row[4]).doubleValue(), // avg_score
	                rank.getAndIncrement()           // 등수
	            ));
	        });
	    });

		 // 모든 DTO를 기수 내 등수 기준으로 정렬 (기수 내 정렬은 위에서 했고, 여기선 전체 기준)
		 // 기수(generation) 내림차순 정렬 → 동일 기수 내에서는 rank 오름차순 정렬
	    dtoList.sort(Comparator.comparing(StudentListResDTO::getGeneration).reversed() // 기수를 int 변환
	        .thenComparing(StudentListResDTO::getRank));  // 기수 내 순위 오름차순
	    dtoList.sort(
	    	    Comparator
	    	        .comparingInt((StudentListResDTO dto) -> Integer.parseInt(dto.getGeneration()))
	    	        .reversed() // 최신 기수 먼저
	    	        .thenComparing(StudentListResDTO::getRank) // 같은 기수 내에서는 등수 오름차순
	    	);
	    // 페이징 객체(PageImpl)로 반환
	    return new PageImpl<>(dtoList, pageable, rawPage.getTotalElements());
	}
	
    /**
     * 전달받은 {@link StudentAddReqDTO}를 기반으로 새로운 학생 회원을 등록합니다.
     *
     * <p>
     * 비밀번호는 {@link PasswordEncoder}를 통해 암호화되며,
     * {@link MemberRole#STUDENT} 역할을 가진 {@link Member} 엔티티로 변환 후 저장소에 저장됩니다.
     * </p>
     *
     * <ul>
     *   <li>DTO의 필드: 기수, 이름, 연락처, 비밀번호, 역할 정보를 포함합니다.</li>
     *   <li>비밀번호는 DB 저장 전에 반드시 암호화되어 저장됩니다.</li>
     *   <li>{@link com.tes.member.domain.repository.StudentRepository#save(Object)}를 통해 저장됩니다.</li>
     * </ul>
     *
     * @param studentAddReqDTO 등록할 학생 정보 DTO
     */
	public void addStudent(StudentAddReqDTO studentAddReqDTO) {
		String encodePassword = passwordEncoder.encode(studentAddReqDTO.getPassword());
		
		Member member = Member.builder()
						      .generation(studentAddReqDTO.getGeneration())
						      .name(studentAddReqDTO.getName())
						      .password(encodePassword)
						      .phone(studentAddReqDTO.getPhone())
						      .role(studentAddReqDTO.getRole())
						      .build();
		
		studentRepository.save(member);
	}
}
