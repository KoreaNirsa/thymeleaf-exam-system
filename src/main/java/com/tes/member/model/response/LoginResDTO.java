package com.tes.member.model.response;

import com.tes.member.domain.entity.Member;
import com.tes.member.enums.MemberRole;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 로그인 성공 후 사용자 정보를 클라이언트에 전달하기 위한 응답 DTO입니다.
 *
 * <p>
 * 사용자 ID, 이름, 역할(Role)을 포함하며,
 * {@link com.tes.member.domain.entity.Member} 엔티티로부터 변환하여 생성됩니다.
 * </p>
 *
 * @see Member
 * @see MemberRole
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor
public class LoginResDTO {
    /** 사용자 고유 식별자 */
    private Long id;

    /** 사용자 이름 */
    private String name;

    /** 사용자 역할 (학생 또는 강사) */
    private MemberRole role;

    /**
     * {@link Member} 엔티티 객체를 기반으로 {@code LoginResDTO}를 생성하는 정적 팩토리 메서드입니다.
     *
     * @param user Member 엔티티
     * @return LoginResDTO 객체
     */
    public static LoginResDTO from(Member user) {
        return LoginResDTO.builder()
                .id(user.getMemberId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
