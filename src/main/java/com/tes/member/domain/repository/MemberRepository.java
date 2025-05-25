package com.tes.member.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tes.member.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByGenerationAndName(String batch, String name);
}