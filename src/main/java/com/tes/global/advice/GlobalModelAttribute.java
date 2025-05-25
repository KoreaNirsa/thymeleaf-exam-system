package com.tes.global.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

/**
 * 전역적으로 모든 컨트롤러에 공통 모델 속성을 추가하는 클래스입니다.
 * <p>
 * 세션에 저장된 로그인 사용자 정보("member")가 존재하는 경우,
 * 해당 정보를 각 컨트롤러에서 Thymeleaf 템플릿 또는 비즈니스 로직에서 사용할 수 있도록
 * {@link Model}에 자동으로 등록합니다.
 * </p>
 * 
 * <p>
 * 예시: 템플릿에서 <code>${member.name}</code>, <code>${member.role}</code> 과 같이 접근 가능
 * </p>
 *
 * @author 
 * @since 1.0
 */
@ControllerAdvice
public class GlobalModelAttribute {
	
    /**
     * 모든 컨트롤러 요청에 앞서 실행되며,
     * 세션에 존재하는 "member" 정보를 Model에 주입합니다.
     *
     * @param model   뷰 렌더링에 사용되는 Spring MVC 모델 객체
     * @param session 현재 사용자의 HTTP 세션
     */
    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpSession session) {
        Object member = session.getAttribute("member");
        if (member != null) {
            model.addAttribute("member", member);
        }
    }
}
