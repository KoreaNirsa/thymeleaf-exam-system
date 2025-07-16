package com.tes.subject.domain.repository;

import java.util.Optional;
import com.tes.subject.domain.entity.EvaluationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 평가 상태 정보를 처리하는 JPA 리포지토리 인터페이스입니다.
 *
 * @author KoreaNirsa
 * @since 1.0
 *
 * @see com.tes.subject.domain.entity.EvaluationStatus
 */
@Repository
public interface EvaluationStatusRepository extends JpaRepository<EvaluationStatus, Long> {

    /**
     * 과목 ID와 회차를 기준으로 평가 상태 정보를 조회합니다.
     *
     * <p>
     * 내부적으로 subject.subjectId = :subjectId AND round = :round 조건으로 조회됩니다.
     * </p>
     *
     * @param subjectId 과목의 고유 식별자
     * @param round 회차 정보 (예: 1차, 2차 등)
     * @return 조회된 {@link EvaluationStatus} 객체를 감싼 Optional
     */
    Optional<EvaluationStatus> findBySubject_SubjectIdAndRound(Long subjectId, int round);
}
