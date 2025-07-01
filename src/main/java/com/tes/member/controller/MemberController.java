package com.tes.member.controller;

import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tes.global.exception.UserException;
import com.tes.member.model.request.LoginReqDTO;
import com.tes.member.model.response.LoginResDTO;
import com.tes.member.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 사용자 인증과 관련된 기능(로그인, 세션 처리 등)을 담당하는 컨트롤러입니다.
 * <p>
 * 로그인 페이지 진입 및 로그인 처리 요청을 받아 처리하며,
 * 로그인 성공 시 사용자 정보를 세션에 저장합니다.
 * </p>
 * 
 * @author 
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService loginService;
	
	/**
	 * 로그인 페이지를 렌더링합니다.
	 *
	 * 타임리프 레이아웃에서 header 영역을 렌더링하지 않기 위해
	 * 모델에 {@code hideHeader=true} 플래그를 전달합니다.
	 *
	 * @param model Thymeleaf 뷰 렌더링을 위한 모델 객체
	 * @return 로그인 페이지 뷰 이름 (pages/login)
	 */
	@GetMapping("/")
	public String Home(Model model) {
	    model.addAttribute("loginReqDTO", new LoginReqDTO());
	    model.addAttribute("hideHeader", true);
		return "pages/login";
	}
	
    /**
     * 로그인 요청을 처리하고 사용자 정보를 세션에 저장한 후,
     * 대시보드 페이지로 리다이렉트합니다.
     *
     * @param loginReqDTO 사용자가 입력한 로그인 요청 DTO
     * @param session     현재 사용자 세션
     * @return 대시보드 페이지로의 리다이렉트 경로
     */
	@PostMapping("/login")
	public String login(@ModelAttribute @Valid LoginReqDTO loginReqDTO, 
							BindingResult bindingResult,
							HttpSession session,
							Model model) {
	    if (bindingResult.hasErrors()) {
		    String errorMsg = bindingResult.getFieldErrors().stream()
		            .map(DefaultMessageSourceResolvable::getDefaultMessage)
		            .collect(Collectors.joining(", "));
		    
	        model.addAttribute("errorMessage", "유효성 검증 실패 : " + errorMsg);
	        return "redirect:/";
	    }
	    
		LoginResDTO response = loginService.login(loginReqDTO);
		session.setAttribute("member", response);
	    return "redirect:/subject";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}