package com.tes.subject.model.response;

import java.time.LocalDate;

import com.tes.subject.domain.entity.EvaluationStatus;
import com.tes.subject.domain.entity.StudentExamSubmission;
import com.tes.subject.domain.entity.Subject;

import lombok.Builder;
import lombok.Getter;

/**
 * 대시보드 화면에서 과목별 평가 현황을 출력하기 위한 응답 DTO입니다.
 * <p>
 * 과목 정보({@link Subject}), 평가 상태({@link EvaluationStatus}),
 * 학생의 시험 제출 결과({@link StudentExamSubmission})를 조합하여
 * 버튼 상태, 점수 및 피드백 여부 등을 판단하고 전달합니다.
 * </p>
 *
 * <p>
 * 화면에서는 각 과목에 대해 버튼 활성화 여부, 스타일 클래스, 평가 상태 문자열 등을
 * 기반으로 UI 렌더링이 동적으로 구성됩니다.
 * </p>
 *
 * @author 
 * @since 1.0
 */
@Getter
@Builder
public class DashboardResDTO {
    /** 과목 ID */
    private Long subjectId;

    /** 과목 이름 */
    private String name;

    /** 1차 평가일 */
    private LocalDate firstExamDate;

    /** 2차 평가일 */
    private LocalDate secondExamDate;

    /** 평가 상태 (예: "시작 전", "평가 시작", "점수 확인", "채점 진행중") */
    private String status;

    /** 버튼에 적용될 CSS 클래스 */
    private String buttonClass;

    /** 버튼 비활성화 여부 */
    private boolean disabled;

    /** 학생의 점수 (null일 경우 미채점) */
    private Integer score;

    /** 학생에게 제공된 피드백 */
    private String feedback;

    /**
     * Subject, EvaluationStatus, StudentExamSubmission 객체를 조합하여 DTO를 생성하는 팩토리 메서드입니다.
     * <p>
     * 평가 상태에 따라 버튼 CSS 클래스 및 활성화 여부를 동적으로 결정합니다.
     * </p>
     *
     * @param subject           과목 엔티티
     * @param evaluationStatus 해당 과목의 평가 상태 (nullable)
     * @param submission        학생의 제출 정보 (nullable)
     * @return DashboardResDTO 인스턴스
     */
    public static DashboardResDTO from(Subject subject, EvaluationStatus evaluationStatus,  StudentExamSubmission submission) {
        String status = evaluationStatus != null ? evaluationStatus.getStatus() : "시작 전";

        String buttonClass;
        boolean disabled;

        switch (status) {
            case "시작 전", "채점 진행중" -> {
                buttonClass = "eval-btn";
                disabled = true;
            }
            case "점수 확인" -> {
                buttonClass = "eval-btn score-check";
                disabled = false;
            }
            case "평가 시작" -> {
                buttonClass = "active";
                disabled = false;
            }
            default -> {
                buttonClass = "";
                disabled = true;
            }
        }

        return DashboardResDTO.builder()
                .subjectId(subject.getSubjectId())
                .name(subject.getName())
                .firstExamDate(subject.getFirstExamDate())
                .secondExamDate(subject.getSecondExamDate())
                .status(status)
                .buttonClass(buttonClass)
                .disabled(disabled)
                .score(submission != null ? submission.getScore() : null)
                .feedback(submission != null ? submission.getFeedback() : null)
                .build();
    }
}
