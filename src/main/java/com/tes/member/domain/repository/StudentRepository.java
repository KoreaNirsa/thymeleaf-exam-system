package com.tes.member.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tes.member.domain.entity.Member;

/**
 * 학생 관련 데이터베이스 조회 기능을 정의하는 레포지토리 인터페이스입니다.
 * <p>
 * 이 인터페이스는 {@code Member} 엔티티를 기반으로 학생 역할(STUDENT)을 가진 사용자 목록을 조회하며,
 * 평균 점수 기준으로 정렬된 결과를 페이징 형태로 제공합니다.
 * </p>
 *
 * <p>
 * 현재는 학생 목록 조회 전용 메서드만 정의되어 있으며,
 * 향후 학생별 제출 이력, 점수 분포, 과목별 필터링 기능 등이 확장될 수 있습니다.
 * </p>
 *
 * @author
 * @since 1.0
 */
public interface StudentRepository extends JpaRepository<Member, Long> {
	
    /**
     * 평균 점수를 기준으로 학생 목록을 페이징 조회합니다.
     * <p>
     * 학생(member.role = 'STUDENT') 정보를 기반으로, 제출된 답안의 평균 점수를 계산하고
     * 이를 내림차순 정렬하여 {@link Pageable} 조건에 맞게 페이징된 데이터를 반환합니다.
     * 답안이 없는 학생도 {@code LEFT JOIN}을 통해 평균 점수 0으로 포함됩니다.
     * </p>
     *
     * @param pageable 페이지 번호, 크기, 정렬 기준 등을 포함한 페이징 요청 객체
     * @return 평균 점수를 포함한 학생 정보(Object 배열)의 페이징 결과
     */
	@Query(
		    value = """
		        SELECT
		            ranked.member_id,
		            ranked.generation,
		            ranked.name,
		            ranked.phone,
		            ranked.avg_score,
		            ranked.rank
		        FROM (
		            SELECT
		                m.member_id,
		                m.generation,
		                m.name,
		                m.phone,
		                ROUND(COALESCE(AVG(s.score), 0), 1) AS avg_score,
		                RANK() OVER (PARTITION BY m.generation ORDER BY AVG(s.score) DESC) AS rank
		            FROM member m
		            LEFT JOIN student_exam_submission s ON m.member_id = s.member_id
		            WHERE m.role = 'STUDENT'
		            GROUP BY m.member_id, m.generation, m.name, m.phone
		        ) AS ranked
		        ORDER BY ranked.generation DESC, ranked.rank ASC
		        """,
		    countQuery = """
		        SELECT COUNT(DISTINCT m.member_id)
		        FROM member m
		        WHERE m.role = 'STUDENT'
		        """,
		    nativeQuery = true
		)
    Page<Object[]> findStudentListWithAvgScore(Pageable pageable);
}
