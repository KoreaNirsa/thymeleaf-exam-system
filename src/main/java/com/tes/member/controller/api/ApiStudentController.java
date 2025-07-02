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

/**
 * 학생 API 요청을 처리하는 컨트롤러입니다.
 *
 * @author
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class ApiStudentController {
	private final StudentService studentService;

	/**
	 * 특정 학생의 비밀번호를 변경합니다.
	 * 
	 * <p>
	 * 	요청 본문으로 전달받은 {@link ChangePasswordReqDTO}를 통해 새 비밀번호와 확인용 비밀번호의 일치 여부를 검증하고,
	 * 	현재 로그인한 사용자 세션 정보를 기반으로 본인 인증을 수행한 뒤, 해당 회원의 비밀번호를 변경합니다.
	 * </p>
	 * 
	 * <ul>
	 * 	<li>비밀번호 유효성 검증 실패 시 400 응답과 함께 에러 메시지를 반환합니다.</li>
	 * 	<li>비밀번호 변경 성공 시 200 응답과 함께 성공 메시지를 반환합니다.</li>
	 * </ul>
	 * 
	 * @param memberId 비밀번호를 변경할 학생의 회원 ID (PathVariable)
	 * @param dto 비밀번호 변경 요청 DTO (요청 본문에 포함)
	 * @param bindingResult 유효성 검증 결과를 담는 객체
	 * @param session 현재 로그인한 사용자의 세션 객체
	 * @return 비밀번호 변경 결과를 담은 {@link ResultVO} 래핑 응답
	 * 
	 * @see com.tes.member.service.StudentService#changePassword(long, String, String, long)
	 * @since 1.0
	 */
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