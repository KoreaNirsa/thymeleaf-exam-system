package com.tes.member.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 학생의 과목별 평가 점수 및 피드백 정보를 담는 응답 DTO입니다.
 *
 * <p>
 * 하나의 과목에 대해 1차 및 2차 평가 점수와 강사 피드백을 포함합니다.
 * 해당 DTO는 {@code StudentExamSubmission} 엔티티 목록을 기반으로 생성됩니다.
 * </p>
 *
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor
public class StudentDetailInfoResDTO {
	private long memberId;
	private String name;
	private String phone;
	private double avgScore;
	private int rank;
	
	private List<SubjectScoreResDTO> subjectScores;
}
