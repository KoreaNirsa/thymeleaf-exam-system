package com.tes.global.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tes.global.exception.UserException;
import com.tes.member.model.request.LoginReqDTO;
import com.tes.member.service.impl.MemberServiceImpl;

/**
 * 전역 예외 처리 클래스
 * 사용자 정의 예외와 공통 예외에 대해 일관된 뷰 응답 제공
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private final Logger logger = LogManager.getLogger(MemberServiceImpl.class);

    /**
     * 사용자 정의 로그인/인증 관련 예외 처리
     */
    @ExceptionHandler(UserException.class)
    public String handleUserException(UserException e, Model model) {
    	logger.warn("UserException 발생: {}", e.getMessage());
    	model.addAttribute("loginReqDTO", new LoginReqDTO());
        model.addAttribute("errorMessage", e.getMessage());
        return "pages/login";
    }

    /**
     * 그 외 알 수 없는 런타임 예외 처리 (옵션)
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, Model model) {
    	logger.error("알 수 없는 예외 발생", e);
        model.addAttribute("errorMessage", "알 수 없는 오류가 발생했습니다.");
        return "error/error"; 
    }
}