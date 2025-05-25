package com.tes.member.model.response;

import com.tes.member.domain.entity.Member;
import com.tes.member.enums.MemberRole;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResDTO {
    private Long id;
    private String name;
    private MemberRole role;

    public static LoginResDTO from(Member user) {
        return LoginResDTO.builder()
                .id(user.getMemberId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
