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

@Entity
@Table(name="member")
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
    private Long memberId;

    private String name;
    private String generation;
    private String phone;
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;
}