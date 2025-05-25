package com.tes.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 로그인 요청을 위한 DTO 클래스입니다.
 *
 * 사용자가 입력한 기수, 이름, 비밀번호 정보를 담아
 * 인증 처리를 위한 서비스 계층으로 전달합니다.
 *
 * 이 클래스는 Thymeleaf 폼과의 바인딩을 위해 기본 생성자와 getter를 제공합니다.
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginReqDTO {
    @NotBlank(message = "기수는 필수 입력입니다.")
    @Size(max = 20, message = "기수는 20자 이내로 입력해주세요.")
    private String generation;

    @NotBlank(message = "이름은 필수 입력입니다.")
    @Size(max = 10, message = "이름은 10자 이내로 입력해주세요.")
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 입력 가능합니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이내로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,20}$",
             message = "비밀번호는 영문 대소문자와 특수문자를 포함해야 합니다.")
    private String password;
}
