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

@Controller
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectController {
	private final SubjectService dashboardService;
	
	@GetMapping("/dashboard")
	public String doDashboard(Model model, @SessionAttribute("member") LoginResDTO member) {
	    Long memberId = member.getId();

		List<DashboardResDTO> subjectList = dashboardService.getDashboardData(memberId);
        model.addAttribute("subjects", subjectList);
        return "pages/dashboard";
	}
}
