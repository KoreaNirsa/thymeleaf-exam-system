package com.tes.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

/**
 * 학생 페이지 요청을 처리하는 컨트롤러입니다.
 * 
 * @author 
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
	
	@GetMapping("/list")
	public String list() {
		return "pages/studentList";
	}

}
