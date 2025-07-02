package com.tes.member.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tes.global.exception.UserException;
import com.tes.member.domain.entity.Member;
import com.tes.member.domain.repository.MemberRepository;
import com.tes.member.domain.repository.StudentRepository;
import com.tes.member.enums.MemberRole;
import com.tes.member.model.request.StudentAddReqDTO;
import com.tes.member.model.response.StudentDetailInfoResDTO;
import com.tes.member.model.response.StudentListResDTO;
import com.tes.member.model.response.SubjectScoreResDTO;
import com.tes.member.service.StudentService;
import com.tes.subject.domain.entity.StudentExamSubmission;
import com.tes.subject.domain.entity.Subject;
import com.tes.subject.domain.repository.StudentExamSubmissionRepository;

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
	private final StudentExamSubmissionRepository studentExamSubmissionRepository;
	private final StudentRepository studentRepository;
	private final MemberRepository memberRepository;
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
	    Page<Object[]> rawPage = studentRepository.findStudentListWithAvgScore(pageable);

	    Map<String, List<Object[]>> groupByGeneration = rawPage.getContent().stream()
	        .collect(Collectors.groupingBy(row -> (String) row[1]));

	    List<StudentListResDTO> dtoList = new ArrayList<>();

	    groupByGeneration.forEach((generation, rows) -> {
	        AtomicInteger rank = new AtomicInteger(1);
	        
	        rows.forEach(row -> {
	            dtoList.add(new StudentListResDTO(
	                ((Long) row[0]).longValue(),     // member_id
	                generation,                      // generation
	                (String) row[2],                 // name
	                (String) row[3],                 // phone
	                ((Double) row[4]).doubleValue(), // avg_score
	                rank.getAndIncrement()           // 등수
	            ));
	        });
	    });

	    dtoList.sort(
	    	    Comparator
	    	        .comparingInt((StudentListResDTO dto) -> Integer.parseInt(dto.getGeneration()))
	    	        .reversed()
	    	        .thenComparing(StudentListResDTO::getRank)
	    	);

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
	
	/**
	 * 주어진 학생 ID를 기반으로 학생의 상세 정보와 과목별 평가 점수 및 피드백 목록을 조회합니다.
	 *
	 * <p>
	 * 이 메서드는 {@link Member} 엔티티를 조회하여 이름, 연락처 등의 기본 정보를 가져오고,
	 * {@link StudentExamSubmission} 엔티티를 통해 과목별 1차/2차 점수와 피드백을 수집합니다.
	 * 과목별로 평가 데이터를 그룹핑하여 {@link SubjectScoreResDTO}로 변환한 후,
	 * {@link StudentDetailInfoResDTO}에 포함시켜 응답 객체로 반환합니다.
	 * </p>
	 *
	 * @param memberId 조회할 학생의 고유 ID
	 * @param avg 학생의 전체 평균 점수
	 * @param rank 학생의 반 내 등수
	 * @return 학생의 기본 정보와 과목별 점수/피드백을 포함한 상세 응답 DTO
	 *
	 * @throws RuntimeException 해당 ID의 학생(Member)을 찾을 수 없는 경우 발생
	 *
	 * @see com.tes.member.domain.entity.Member
	 * @see com.tes.subject.domain.entity.Subject
	 * @see com.tes.evaluation.domain.entity.StudentExamSubmission
	 * @see com.tes.member.response.StudentDetailInfoResDTO
	 * @see com.tes.member.response.SubjectScoreResDTO
	 * @since 1.0
	 */
	public StudentDetailInfoResDTO getStudentDetail(long memberId, double avg, int rank) {
	    Member member = memberRepository.findByMemberId(memberId)
	        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

	    List<StudentExamSubmission> submissions = studentExamSubmissionRepository.findByMember_MemberId(memberId);

	    Map<Subject, List<StudentExamSubmission>> grouped = submissions.stream()
	        .collect(Collectors.groupingBy(StudentExamSubmission::getSubject));

	    List<SubjectScoreResDTO> subjectScores = grouped.entrySet().stream()
	        .map(entry -> {
	            Subject subject = entry.getKey();
	            List<StudentExamSubmission> exams = entry.getValue();

	            Integer firstScore = null;
	            Integer secondScore = null;
	            String firstFeedback = null;
	            String secondFeedback = null;

	            for (StudentExamSubmission s : exams) {
	                if (s.getRound() == 1) {
	                    firstScore = s.getScore();
	                    firstFeedback = s.getFeedback();
	                } else if (s.getRound() == 2) {
	                    secondScore = s.getScore();
	                    secondFeedback = s.getFeedback();
	                }
	            }

	            return SubjectScoreResDTO.builder()
	                .subjectName(subject.getName())
	                .firstScore(firstScore)
	                .secondScore(secondScore)
	                .firstFeedback(firstFeedback)
	                .secondFeedback(secondFeedback)
	                .build();
	        })
	        .toList();

	    return StudentDetailInfoResDTO.builder()
	        .memberId(memberId)
	        .name(member.getName())
	        .phone(member.getPhone())
	        .avgScore(avg)
	        .rank(rank)
	        .subjectScores(subjectScores)
	        .build();
	}
	
	/**
	 * 사용자의 비밀번호를 변경합니다.
	 * 
	 * <p>
	 * 	다음 조건을 만족할 경우에만 비밀번호를 변경합니다.
	 * </p>
	 * <ul>
	 *   <li>요청한 사용자 ID({@code sessionMemberId})와 대상 사용자 ID({@code memberId})가 일치해야 합니다.</li>
	 *   <li>새 비밀번호({@code newPassword})와 확인 비밀번호({@code confirmPassword})가 일치해야 합니다.</li>
	 *   <li>회원 정보가 존재해야 하며, 존재하지 않을 경우 {@link IllegalArgumentException}이 발생합니다.</li>
	 * </ul>
	 * 
	 * <p>
	 * 	모든 조건을 통과하면 새 비밀번호를 인코딩하여 DB에 저장합니다.
	 * </p>
	 * 
	 * @param memberId 비밀번호를 변경할 대상 회원의 ID
	 * @param newPassword 새 비밀번호 (암호화 대상)
	 * @param confirmPassword 새 비밀번호 확인 (일치 여부 검사)
	 * @param sessionMemberId 세션에서 로그인된 사용자 ID (본인 확인용)
	 * 
	 * @throws UserException 세션 사용자와 요청 대상이 다르거나, 비밀번호 불일치 시 발생
	 * @throws IllegalArgumentException 사용자가 존재하지 않는 경우 발생
	 * 
	 * @see com.tes.member.entity.Member#changePassword(String)
	 * @since 1.0
	 */
	@Transactional
	public void changePassword(long memberId, String newPassword, String confirmPassword, long sessionMemberId) {
	
	    if (sessionMemberId != memberId) {
	        throw new UserException("다른 사용자는 요청 불가");
	    }
        
	    if (!Objects.equals(newPassword, confirmPassword)) {
			throw new UserException("비밀번호 불일치");
	    }
	    
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
		
        String encodedPassword = passwordEncoder.encode(newPassword);
        member.changePassword(encodedPassword);
	}
}
