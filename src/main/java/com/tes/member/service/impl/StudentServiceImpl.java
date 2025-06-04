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
import org.springframework.stereotype.Service;

import com.tes.member.domain.repository.StudentRepository;
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
	
    /**
     * 학생 목록을 페이징 처리하여 조회합니다.
     * <p>
     * 학생별 평균 점수를 기준으로 내림차순 정렬된 결과를 반환하며,
     * 페이지 번호와 크기(Pageable)를 기반으로 한 페이징 처리 및 등수(rank)도 포함됩니다.
     * </p>
     *
     * @param pageable 페이징 요청 정보 (페이지 번호, 페이지 크기 등)
     * @return 평균 점수 기준 내림차순 정렬된 학생 리스트 페이지
     */
	@Override
	public Page<StudentListResDTO> getStudentPage(Pageable pageable) {
	    Page<Object[]> rawPage = studentRepository.findStudentListWithAvgScore(pageable);

	    // List<Object[]> → Group by generation
	    Map<String, List<Object[]>> groupedByGeneration = rawPage.getContent().stream()
	        .collect(Collectors.groupingBy(row -> (String) row[1]));

	    List<StudentListResDTO> dtoList = new ArrayList<>();

	    groupedByGeneration.forEach((generation, rows) -> {
	        AtomicInteger rank = new AtomicInteger(1);
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

	    // 기수 내에서 등수는 맞지만 전체 페이지 기준 정렬 필요
	    dtoList.sort(Comparator.comparing(StudentListResDTO::getGeneration).reversed()
	        .thenComparing(StudentListResDTO::getRank)); // generation DESC, rank ASC

	    return new PageImpl<>(dtoList, pageable, rawPage.getTotalElements());
	}
}
