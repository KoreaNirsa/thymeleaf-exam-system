package com.tes.member.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SubjectScoreResponseDTO {
    private String subjectName;
    private Integer firstScore;
    private Integer secondScore;
    private String firstFeedback;
    private String secondFeedback;
}
