package com.tes.member.domain.entity;

import com.tes.member.enums.MemberRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 시스템에 등록된 사용자(학생 또는 강사)를 나타내는 JPA 엔티티입니다.
 * <p>
 * 각 회원은 고유 식별자(memberId), 이름, 기수, 연락처, 비밀번호, 역할(Role)을 가집니다.
 * 역할은 {@link MemberRole} Enum을 사용하며, STUDENT 또는 INSTRUCTOR로 구분됩니다.
 * </p>
 * 
 * <p>
 * DB 테이블명은 <code>member</code>로 매핑됩니다.
 * </p>
 * 
 * @author 
 * @since 1.0
 */
@Entity
@Table(name="member")
@Getter
@Setter
public class Member {
    /** 회원 고유 식별자 */
    @Id
    @GeneratedValue
    private Long memberId;

    /** 사용자 이름 */
    private String name;

    /** 기수 (예: 1기, 2기) */
    private String generation;

    /** 사용자 연락처 */
    private String phone;

    /** 사용자 비밀번호 */
    private String password;

    /** 사용자 역할 (STUDENT or INSTRUCTOR) */
    @Enumerated(EnumType.STRING)
    private MemberRole role;
}