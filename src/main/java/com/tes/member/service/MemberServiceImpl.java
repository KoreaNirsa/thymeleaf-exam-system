package com.tes.member.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tes.global.exception.UserException;
import com.tes.member.domain.entity.Member;
import com.tes.member.domain.repository.MemberRepository;
import com.tes.member.model.request.LoginReqDTO;
import com.tes.member.model.response.LoginResDTO;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final Logger logger = LogManager.getLogger(MemberServiceImpl.class);

	public LoginResDTO login(LoginReqDTO loginReqDTO) {
		Member user = userRepository.findByGenerationAndName(loginReqDTO.getGeneration(), loginReqDTO.getName())
				.orElseThrow(() -> {
					logger.warn("존재하지 않는 사용자: 기수={}, 이름={}", loginReqDTO.getGeneration(), loginReqDTO.getName());
					return new UserException("아이디가 존재하지 않습니다.", "pages/login");
				});

		if (!passwordEncoder.matches(loginReqDTO.getPassword(), user.getPassword())) {
			logger.warn("비밀번호 불일치: 사용자 ID = {}", user.getMemberId());
			throw new UserException("비밀번호가 틀렸습니다.", "pages/login");
		}

		return LoginResDTO.from(user);
	}

}