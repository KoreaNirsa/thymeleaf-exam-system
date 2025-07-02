package com.tes.auth.service;

import com.tes.auth.model.request.LoginReqDTO;
import com.tes.auth.model.response.LoginResDTO;

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
public interface AuthService {
	
	public LoginResDTO login(LoginReqDTO loginReqDTO);
}
