package com.tes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
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
	    model.addAttribute("hideHeader", true);
		return "pages/login";
	}

}