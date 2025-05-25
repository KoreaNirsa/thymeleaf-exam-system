package com.tes.subject.model.response;

import java.time.LocalDate;

import com.tes.subject.domain.entity.EvaluationStatus;
import com.tes.subject.domain.entity.StudentExamSubmission;
import com.tes.subject.domain.entity.Subject;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardResDTO {
    private Long subjectId;
    private String name;
    private LocalDate firstExamDate;
    private LocalDate secondExamDate;
    private String status;
    private String buttonClass;
    private boolean disabled;
    
    // scoreModal 용 필드
    private Integer score;
    private String feedback;

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
