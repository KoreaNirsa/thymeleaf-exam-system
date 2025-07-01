package com.tes.member.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StudentDetailInfoResDTO {
	private long memberId;
	private String name;
	private String phone;
	private double avgScore;
	private int rank;
	
	private List<SubjectScoreResponseDTO> subjectScores;
}
