package com.tes.subject.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.tes.subject.domain.entity.Subject;
import com.tes.subject.model.response.DashboardResDTO;

/**
 * {@link Subject} 엔티티에 대한 읽기 전용 JPA 리포지토리입니다.
 * 
 * <p>
 * Spring Data의 {@link Repository} 인터페이스를 확장하며,
 * 과목 리스트 조회 및 관련 평가 상태 정보를 함께 조회하는 커스텀 쿼리를 제공합니다.
 * </p>
 *
 * <p>
 * 이 리포지토리는 과목 목록과 해당 과목의 1차 평가 상태(또는 평가 상태가 없는 경우)를
 * 함께 로딩하기 위해 {@code LEFT JOIN FETCH}를 사용합니다.
 * </p>
 *
 * @author 
 * @since 1.0
 */
public interface SubjectRepository extends Repository<Subject, Long> {
	
    /**
     * 과목 목록을 조회하면서 각 과목의 1차 평가 상태 정보를 함께 로딩합니다.
     * 현재는 1차 평가로 하드코딩 되어 있으며, 추후 수정할 예정입니다.
     * 
     * <p>
     * 평가 상태가 존재하지 않는 과목도 함께 포함됩니다.
     * </p>
     *
     * @return 1차 평가 상태 또는 미지정 상태가 포함된 과목 리스트
     */
    @Query("""
            SELECT s FROM Subject s
            LEFT JOIN FETCH s.evaluationStatuses es
            WHERE es.round = 1 OR es.round IS NULL
        """)
        List<Subject> findAllSubjectsWithFirstExamStatus();
}