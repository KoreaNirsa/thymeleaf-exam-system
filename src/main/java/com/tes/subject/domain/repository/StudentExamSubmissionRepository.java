package com.tes.subject.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tes.subject.domain.entity.StudentExamSubmission;

/**
 * 학생의 과목별 시험 제출 정보를 관리하는 JPA 리포지토리입니다.
 * <p>
 * {@link StudentExamSubmission} 엔티티에 대한 CRUD 및 특정 조건 기반 조회 기능을 제공합니다.
 * </p>
 *
 * <p>
 * 사용자 ID, 과목 ID, 평가 회차를 기준으로 특정 시험 제출 내역을 조회하는 쿼리 메서드를 포함하고 있으며,
 * 이는 평가 수정, 채점, 피드백 입력 등의 기능에서 활용됩니다.
 * </p>
 * 
 * @see StudentExamSubmission
 * @since 1.0
 */
@Repository
public interface StudentExamSubmissionRepository extends JpaRepository<StudentExamSubmission, Long> {
    
	/**
     * 회원 ID, 과목 ID, 회차 정보를 기반으로 시험 제출 내역을 조회합니다.
     *
     * @param memberId  제출한 학생의 ID
     * @param subjectId 과목 ID
     * @param round     평가 회차 (1차 = 1, 2차 = 2)
     * @return 해당 조건에 해당하는 시험 제출 엔티티(Optional)
     */
	Optional<StudentExamSubmission> findByMember_MemberIdAndSubject_SubjectIdAndRound(Long memberId, Long subjectId, int round);
	
    List<StudentExamSubmission> findByMember_MemberId(Long memberId);
}
