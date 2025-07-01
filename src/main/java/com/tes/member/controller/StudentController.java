package com.tes.member.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tes.member.enums.MemberRole;
import com.tes.member.model.request.StudentAddReqDTO;
import com.tes.member.model.response.StudentDetailInfoResDTO;
import com.tes.member.model.response.StudentListResDTO;
import com.tes.member.service.StudentService;

import lombok.RequiredArgsConstructor;

/**
 * 학생 페이지 요청을 처리하는 컨트롤러입니다.
 * <p>
 * 학생 관련 기능(학생 관리, 학생 추가, 상세보기 등)을 처리합니다.
 * </p>
 * 
 * @author 
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
	private final StudentService studentService;
	
	/**
	 * 학생 목록 페이지를 반환합니다.
	 * <p>
	 * 이 컨트롤러는 학생들의 기수, 이름, 연락처, 평균 점수 및 등수 정보를 포함한
	 * 페이징된 학생 리스트를 조회하여 뷰에 전달합니다.
	 * </p>
	 *
	 * <ul>
	 *   <li>페이징 처리를 위해 {@link Pageable}을 사용합니다.</li>
	 *   <li>서비스로부터 {@link Page}&lt;StudentListResDTO&gt;를 받아 리스트 및 페이지 정보를 모델에 담습니다.</li>
	 *   <li>Thymeleaf 템플릿: {@code templates/pages/studentList.html}</li>
	 * </ul>
	 *
	 * @param pageable 페이징 요청 정보 (기본 페이지 크기 10)
	 * @param model 뷰에 데이터를 전달할 Spring MVC 모델
	 * @return 학생 리스트 페이지 템플릿 경로
	 */
	@GetMapping
	public String list(@PageableDefault(size = 10) Pageable pageable, Model model) {
	    Page<StudentListResDTO> page = studentService.getStudentPage(pageable);
	    
	    model.addAttribute("students", page.getContent());
	    model.addAttribute("pageInfo", page);
	    
		return "pages/studentList";
	}
	
    /**
     * 새로운 학생을 등록합니다.
     * <p>
     * 전달받은 {@link StudentAddReqDTO} 객체를 기반으로 학생 정보를 저장하며,
     * 저장 후에는 학생 목록 페이지로 리다이렉트됩니다.
     * </p>
     *
     * <ul>
     *   <li>{@code role} 값은 {@link MemberRole#STUDENT}로 강제 설정됩니다.</li>
     *   <li>학생 등록 처리는 {@link com.example.service.StudentService#addStudent(StudentAddReqDTO)}에 위임됩니다.</li>
     *   <li>정상 등록 후 {@code /student/list} 페이지로 리다이렉트됩니다.</li>
     * </ul>
     *
     * @param studentAddReqDTO 등록할 학생 정보 요청 DTO
     * @return 등록 후 학생 목록 페이지로의 리다이렉트 경로
     */
	@PostMapping
	public String addStudent(@ModelAttribute StudentAddReqDTO studentAddReqDTO) {
		studentAddReqDTO.setRole(MemberRole.STUDENT);
		
		studentService.addStudent(studentAddReqDTO);
		
		return "redirect:/student";
	}
	
	@GetMapping("/{memberId}")
	public String detail(@PathVariable("memberId") long memberId,
						 @RequestParam("avg") double avg,
						 @RequestParam("rank") int rank,
						 Model model) {
		StudentDetailInfoResDTO detailInfo = studentService.getStudentDetail(memberId, avg, rank);
		model.addAttribute("detailInfo", detailInfo);
		
		return "pages/studentDetail";
	}
}
