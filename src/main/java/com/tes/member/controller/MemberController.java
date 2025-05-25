package com.tes.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tes.member.model.request.LoginReqDTO;
import com.tes.member.model.response.LoginResDTO;
import com.tes.member.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

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
	
	@PostMapping("/login")
	public String doLogin(@ModelAttribute LoginReqDTO loginReqDTO, HttpSession session) {
		LoginResDTO response = loginService.login(loginReqDTO);
		session.setAttribute("member", response);
	    return "redirect:/subject/dashboard";
	}

}