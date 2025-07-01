package com.tes.member.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 학생 상세 정보를 클라이언트에 전달하기 위한 응답 DTO입니다.
 *
 * <p>
 * 이름, 연락처, 평균 점수, 반 내 등수와 더불어,
 * 수강 중인 과목별 평가 점수 및 피드백 목록을 포함합니다.
 * 해당 DTO는 {@link com.tes.member.domain.entity.Member} 엔티티를 기반으로 생성되며,
 * 평가 정보는 {@code StudentExamSubmission}을 기준으로 변환됩니다.
 * </p>
 *
 * @see SubjectScoreResponseDTO
 * @since 1.0
 */
@Getter
@Builder
@ToString
public class SubjectScoreResDTO {
    private String subjectName;
    private Integer firstScore;
    private Integer secondScore;
    private String firstFeedback;
    private String secondFeedback;
}
