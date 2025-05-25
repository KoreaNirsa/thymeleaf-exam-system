package com.tes.subject.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.tes.member.model.response.LoginResDTO;
import com.tes.subject.model.response.DashboardResDTO;
import com.tes.subject.service.SubjectService;

import lombok.RequiredArgsConstructor;

/**
 * 과목 및 대시보드 관련 요청을 처리하는 컨트롤러입니다.
 * 
 * @author 
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectController {
	private final SubjectService dashboardService;
	
    /**
     * 대시보드 페이지를 렌더링합니다.
     * <p>
	 * 현재 로그인한 사용자의 정보를 기반으로, 과목별 평가 현황을 조회하여
	 * 대시보드 페이지에 렌더링합니다.
	 * </p>
     *
     * @param model  Thymeleaf 뷰 렌더링을 위한 모델 객체
     * @param member 세션에 저장된 로그인 사용자 정보
     * @return 대시보드 템플릿 경로 ("pages/dashboard")
     */
	@GetMapping("/dashboard")
	public String doDashboard(Model model, @SessionAttribute("member") LoginResDTO member) {
	    Long memberId = member.getId();

		List<DashboardResDTO> subjectList = dashboardService.getDashboardData(memberId);
        model.addAttribute("subjects", subjectList);
        return "pages/dashboard";
	}
}
