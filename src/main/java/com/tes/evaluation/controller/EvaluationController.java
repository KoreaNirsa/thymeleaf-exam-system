package com.tes.evaluation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

/**
 * 평가 페이지 요청을 처리하는 컨트롤러입니다.
 * 
 * @author 
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/evaluation")
public class EvaluationController {

	@GetMapping
	public String page() {
		return "pages/evaluation";
	}
	
	// 요청 확인용 임시 작성
	@PostMapping("/{studentExamSubmissionId}")
	public String feedbackUpdate(@PathVariable("studentExamSubmissionId") long studentExamSubmissionId) {
		System.out.println("----------------");
		System.out.println(studentExamSubmissionId);
		return "pages/evaluation";
	}
}
