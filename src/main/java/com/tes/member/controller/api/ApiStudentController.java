package com.tes.member.controller.api;

import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tes.global.model.response.ResultVO;
import com.tes.member.model.request.ChangePasswordReqDTO;
import com.tes.member.model.response.LoginResDTO;
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
    public ResponseEntity<ResultVO<String>> updatePassword(
            @PathVariable("memberId") long memberId,
            @RequestBody @Valid ChangePasswordReqDTO dto,
            BindingResult bindingResult,
            HttpSession session) {
    	
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest()
                    .body(ResultVO.error("유효성 검증 실패", errorMsg));
        }
        
        LoginResDTO sessionAttr = (LoginResDTO) session.getAttribute("member");

        studentService.changePassword(memberId, dto.getNewPassword(), dto.getConfirmPassword(), sessionAttr.getMemberId());

        return ResponseEntity.ok(ResultVO.success("비밀번호 변경 성공"));
    }
}