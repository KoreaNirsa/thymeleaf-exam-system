package com.nirsa_exam_project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
	/**
	 * 메인 페이지 조회
	 * @return 메인 페이지 뷰 호출
	 */
	@GetMapping("/")
	public String Home() {
		return "pages/studentDetail";
	}

}