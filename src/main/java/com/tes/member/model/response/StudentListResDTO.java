package com.tes.member.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 학생 목록 조회 시 사용되는 응답 DTO입니다.
 * <p>
 * 	학생의 기본 정보(기수, 이름, 연락처)와 성적 요약(평균 점수, 등수)을 포함합니다.
 * 	주로 학생 리스트 페이지에서 사용됩니다.
 * </p>
 * 
 * @author
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class StudentListResDTO {
    private long memberId;
    private String generation;
    private String name;
    private String phone;
    private double avgScore;
    private int rank;
}
