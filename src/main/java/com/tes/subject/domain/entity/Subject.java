package com.tes.subject.domain.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 평가 대상 과목 정보를 나타내는 JPA 엔티티입니다.
 * <p>
 * 각 과목은 이름, 1차/2차 시험일을 가지며, 
 * 과목별로 여러 평가 상태({@link EvaluationStatus})와 연결될 수 있습니다.
 * </p>
 *
 * <p>
 * 과목 정보는 강사 페이지의 과목 관리, 평가 페이지의 시험 생성 등에 사용되며,
 * 학생의 평가 대상 과목 목록에도 포함됩니다.
 * </p>
 *
 * @author 
 * @since 1.0
 */
@Entity
@Table(name="subject")
@Getter
@Setter
public class Subject {

    /** 과목 고유 식별자 (PK) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    /** 과목명 */
    private String name;

    /** 1차 평가 예정일 */
    private LocalDate firstExamDate;

    /** 2차 평가 예정일 */
    private LocalDate secondExamDate;

    /** 과목별 평가 상태 목록 (1차, 2차 각각 존재 가능) */
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<EvaluationStatus> evaluationStatuses;
}