package com.tes.subject.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 피드백 수정 요청 DTO
 *
 * <p>학생 시험 제출에 대한 피드백을 수정할 때 사용하는 요청 데이터입니다.</p>
 *
 * - 과목 ID와 제출 ID는 1 이상의 값을 가져야 합니다.
 * - 피드백 제목과 내용은 공백이거나 비어 있을 수 없으며,
 *   내용은 최대 1000자까지 작성 가능합니다.
 *
 * @author KoreaNirsa
 */
@Setter
@Getter
@NoArgsConstructor
public class FeedbackEditReqDTO {
    /**
     * 회원 ID (1 이상)
     */
    @Min(value = 1, message = "회원이 아닙니다.")
    private Long memberId;

    /**
     * 평균 점수
     */
    private Double avgScore;

    /**
     * 순위
     */
    private Long rank;

    /**
     * 과목 ID (1 이상)
     */
    @Min(value = 1, message = "과목 ID는 1 이상이어야 합니다.")
    private Long subjectId;

    /**
     * 학생 시험 제출 ID (1 이상)
     */
    @Min(value = 1, message = "학생 시험 제출 ID는 1 이상이어야 합니다.")
    private Long studentExamSubmissionId;

    /**
     * 피드백 내용 (비어 있을 수 없고, 1000자 이하)
     */
    @NotBlank(message = "피드백 내용을 입력해주세요.")
    @Size(max = 1000, message = "피드백 내용은 1000자 이하로 입력해주세요.")
    private String feedbackContent;
}