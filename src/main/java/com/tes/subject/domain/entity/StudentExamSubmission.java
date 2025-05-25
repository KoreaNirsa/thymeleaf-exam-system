package com.tes.subject.domain.entity;

import com.tes.member.domain.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
/**
 * 학생의 과목별 시험 제출 정보를 저장하는 JPA 엔티티입니다.
 * <p>
 * 각 제출은 특정 학생({@link Member})과 과목({@link Subject})에 대한
 * 회차별(1차, 2차) 코드 답안, 채점 점수, 피드백을 포함합니다.
 * </p>
 *
 * <p>
 * 테이블명은 <code>student_exam_submission</code>이며, 
 * 채점과 피드백 입력 기능, 학생 평가 결과 열람 기능 등에 사용됩니다.
 * </p>
 *
 * @author 
 * @since 1.0
 */
@Entity
@Table(name="student_exam_submission")
@Getter
@Setter
public class StudentExamSubmission {

    /** 제출 고유 식별자 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentExamSubmissionId;

    /** 제출한 학생 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /** 제출한 과목 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    /** 회차 (1차 = 1, 2차 = 2) */
    private int round;

    /** 학생이 작성한 코드 답안 */
    @Lob
    private String codeAnswer;

    /** 채점 점수 (0~100), null일 경우 미채점 상태 */
    private Integer score;

    /** 강사 피드백 (최대 1000자) */
    @Column(length = 1000)
    private String feedback;
}