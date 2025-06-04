package com.tes.member.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
