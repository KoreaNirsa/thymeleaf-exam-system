package com.tes.member.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tes.member.domain.entity.Member;

/**
 * {@link Member} 엔티티에 대한 데이터베이스 접근을 담당하는 JPA 리포지토리 인터페이스입니다.
 * <p>
 * Spring Data JPA가 제공하는 {@link JpaRepository}를 상속받아,
 * 기본적인 CRUD 기능과 추가적인 사용자 정의 쿼리 메서드를 사용할 수 있습니다.
 * </p>
 * 
 * @author 
 * @since 1.0
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
	
    /**
     * 기수와 이름을 기반으로 회원 정보를 조회합니다.
     *
     * @param generation 조회할 회원의 기수
     * @param name  조회할 회원의 이름
     * @return 해당 기수와 이름을 가진 회원이 존재할 경우 {@link Optional}에 래핑된 {@link Member} 객체
     */
    Optional<Member> findByGenerationAndName(String generation, String name);

	Optional<Member> findByMemberId(long memberId);

	void save(String encodedPassword);
}