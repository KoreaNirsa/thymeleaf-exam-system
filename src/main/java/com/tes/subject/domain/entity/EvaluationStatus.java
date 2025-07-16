package com.tes.subject.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 과목별 평가 진행 상태를 나타내는 JPA 엔티티입니다.
 * <p>
 * 각 {@link Subject} 과목에 대해 1차 또는 2차 평가의 시작/종료 상태를 저장합니다.
 * </p>
 * 
 * <p>
 * 테이블명은 <code>evaluation_status</code>이며, 평가 회차 및 상태값을 기준으로
 * 강사와 학생의 화면 및 기능 동작을 제어합니다.
 * </p>
 *
 * @author 
 * @since 1.0
 */
@Entity
@Table(name="evaluation_status")
@Getter
@Setter
public class EvaluationStatus {
    /** 평가 상태 고유 식별자 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluationStatusId;

    /** 평가가 속한 과목 (N:1 관계) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    /** 평가 상태 (예: 시작 대기, 평가 시작, 채점 진행중, 점수 확인) */
    private String status;

    /** 평가 회차 (1차 = 1, 2차 = 2) */
    private int round;
}