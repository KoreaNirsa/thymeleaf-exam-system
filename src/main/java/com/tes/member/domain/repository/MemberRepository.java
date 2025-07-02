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

    /**
     * 회원 ID를 기반으로 회원 정보를 조회합니다.
     *
     * @param memberId 조회할 회원의 고유 ID
     * @return 해당 ID에 해당하는 {@link Member} 객체를 감싼 {@link Optional}  
     *         (존재하지 않으면 빈 Optional 반환)
     *
     * @since 1.0
     */
	Optional<Member> findByMemberId(long memberId);

	/**
	 * 암호화된 비밀번호를 저장합니다.
	 * 
	 * <p>
	 * 이 메서드는 기존 회원의 비밀번호를 새 값으로 업데이트하는 용도로 사용됩니다.
	 * 내부적으로 어떤 회원의 비밀번호를 갱신할지는 구현 클래스에 따라 달라집니다.
	 * </p>
	 *
	 * @param encodedPassword 암호화된 비밀번호 문자열
	 * @since 1.0
	 */
	void save(String encodedPassword);
}