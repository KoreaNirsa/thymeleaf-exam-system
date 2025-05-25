package com.tes.subject.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.tes.subject.domain.entity.Subject;
import com.tes.subject.model.response.DashboardResDTO;

public interface SubjectRepository extends Repository<Subject, Long> {

    @Query("""
            SELECT s FROM Subject s
            LEFT JOIN FETCH s.evaluationStatuses es
            WHERE es.round = 1 OR es.round IS NULL
        """)
        List<Subject> findAllSubjectsWithFirstExamStatus();
}