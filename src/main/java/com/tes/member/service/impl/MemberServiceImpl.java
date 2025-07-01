package com.tes.member.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tes.global.exception.UserException;
import com.tes.member.domain.entity.Member;
import com.tes.member.domain.repository.MemberRepository;
import com.tes.member.model.request.LoginReqDTO;
import com.tes.member.model.response.LoginResDTO;
import com.tes.member.service.MemberService;

import lombok.RequiredArgsConstructor;

/**
 * {@link MemberService} 인터페이스의 구현체로,
 * 사용자 관련 로직을 처리하는 서비스 클래스입니다.
 * 
 * @author 김재섭
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    /** 회원 정보 조회를 위한 JPA 리포지토리 */
	private final MemberRepository userRepository;
	
    /** 비밀번호 암호화 및 비교를 위한 Spring Security PasswordEncoder */
	private final PasswordEncoder passwordEncoder;
	
    /** 인증 과정에서 로그를 기록하기 위한 Logger */
	private final Logger logger = LogManager.getLogger(MemberServiceImpl.class);

    /**
     * 사용자 로그인 요청을 처리합니다.
     *
     * <p>
	 * 사용자의 기수와 이름으로 회원을 조회한 뒤,
	 * 비밀번호를 비교하여 로그인 성공 여부를 판단합니다.
	 * 인증 실패 시 {@link UserException}을 발생시켜 사용자에게 적절한 메시지를 전달합니다.
	 * </p>
	 * 
     * @param loginReqDTO 사용자의 기수, 이름, 비밀번호 정보
     * @return 로그인 성공 시 사용자 정보를 담은 응답 DTO
     * @throws UserException 아이디 또는 비밀번호가 일치하지 않을 경우 예외 발생
     */
	public LoginResDTO login(LoginReqDTO loginReqDTO) {
		Member user = userRepository.findByGenerationAndName(loginReqDTO.getGeneration(), loginReqDTO.getName())
				.orElseThrow(() -> {
					logger.warn("존재하지 않는 사용자: 기수={}, 이름={}", loginReqDTO.getGeneration(), loginReqDTO.getName());
					return new UserException("아이디가 존재하지 않습니다.");
				});

		if (!passwordEncoder.matches(loginReqDTO.getPassword(), user.getPassword())) {
			logger.warn("비밀번호 불일치: 사용자 ID = {}", user.getMemberId());
			throw new UserException("비밀번호가 틀렸습니다.");
		}

		return LoginResDTO.from(user);
	}

}