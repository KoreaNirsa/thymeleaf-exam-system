package com.tes.subject.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tes.subject.domain.entity.StudentExamSubmission;

@Repository
public interface StudentExamSubmissionRepository extends JpaRepository<StudentExamSubmission, Long> {
    Optional<StudentExamSubmission> findByMember_MemberIdAndSubject_SubjectIdAndRound(Long memberId, Long subjectId, int round);
}
