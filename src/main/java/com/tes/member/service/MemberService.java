package com.tes.member.service;

import com.tes.member.model.request.LoginReqDTO;
import com.tes.member.model.response.LoginResDTO;

/**
 * 회원 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 * <p>
 * 현재는 로그인 기능만 정의되어 있으며,
 * 향후 회원 가입, 정보 수정, 비밀번호 변경 등 다양한 회원 관련 기능을 확장할 수 있습니다.
 * </p>
 * 
 * <p>
 * 해당 인터페이스는 {@code MemberServiceImpl} 클래스에서 구현됩니다.
 * </p>
 *
 * @author 
 * @since 1.0
 */
public interface MemberService {
	
    /**
     * 사용자의 로그인 요청을 처리합니다.
     * <p>
     * 기수, 이름, 비밀번호를 기반으로 사용자 인증을 수행하고,
     * 인증에 성공하면 사용자 정보를 응답 DTO로 반환합니다.
     * </p>
     *
     * @param loginReqDTO 로그인 요청 정보를 담은 DTO
     * @return 로그인에 성공한 사용자 정보를 담은 DTO
     * @throws com.tes.global.exception.UserException 로그인 실패 시 예외 발생
     */
	public LoginResDTO login(LoginReqDTO loginReqDTO);
}
