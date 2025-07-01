package com.tes.member.controller.api;

import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tes.member.model.request.ChangePasswordReqDTO;
import com.tes.member.service.StudentService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class ApiStudentController {
	private final StudentService studentService;

    @PatchMapping("/{memberId}")
    public ResponseEntity<String> updatePassword(
            @PathVariable("memberId") long memberId,
            @RequestBody @Valid ChangePasswordReqDTO dto,
            BindingResult bindingResult,
            HttpSession session) {
    	if (bindingResult.hasErrors()) {
    		String errorMsg = bindingResult.getFieldErrors().stream()
    				.map(DefaultMessageSourceResolvable::getDefaultMessage)
    				.collect(Collectors.joining(", "));
    		return ResponseEntity.badRequest().body("유효성 검증 실패 : " + errorMsg);
    	}
    	if((long)session.getAttribute("member.memberId") == memberId) {
    		studentService.changePassword(memberId, dto.getNewPassword(), dto.getConfirmPassword());
    		return ResponseEntity.ok("success");
    	}

    	return ResponseEntity.badRequest().body("유효한 사용자가 아닙니다.");
    }
}