package com.tes.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordReqDTO {
	
    /** 비밀번호 - 필수 입력, 8~20자, 영문 대소문자 + 특수문자 포함 필수 */
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 50자 이내로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\W).{8,50}$",
             message = "비밀번호는 영문 대소문자와 특수문자를 포함해야 합니다.")
    private String newPassword;
    
    @NotBlank(message = "비밀번호 확인은 필수 입력입니다.")
    @Size(min = 8, max = 20, message = "비밀번호 확인은 8자 이상 50자 이내로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\W).{8,50}$",
             message = "비밀번호 확인은 영문 대소문자와 특수문자를 포함해야 합니다.")
    private String confirmPassword;
}