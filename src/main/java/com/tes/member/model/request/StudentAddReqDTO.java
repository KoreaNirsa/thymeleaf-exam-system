package com.tes.member.model.request;

import com.tes.member.enums.MemberRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentAddReqDTO {
	/** 기수 (예: 1, 2) - 필수 입력, 최대 20자 */
    @NotBlank(message = "기수는 필수 입력입니다.")
    @Size(max = 20, message = "기수는 20자 이내로 입력해주세요.")
	private String generation;

    /** 이름 - 필수 입력, 한글만 허용, 최대 10자 */
    @NotBlank(message = "이름은 필수 입력입니다.")
    @Size(max = 10, message = "이름은 10자 이내로 입력해주세요.")
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 입력 가능합니다.")
	private String name;

    /** 비밀번호 - 필수 입력, 8~20자, 영문 대소문자 + 특수문자 포함 필수 */
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 50자 이내로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\W).{8,50}$",
             message = "비밀번호는 영문 대소문자와 특수문자를 포함해야 합니다.")
	private String password;
	
    /** 연락처 - 필수 입력, 형식: 000-0000-0000 */
    @NotBlank(message = "연락처는 필수 입력입니다.")
    @Pattern(
        regexp = "^\\d{3}-\\d{4}-\\d{4}$",
        message = "연락처는 000-0000-0000 형식으로 입력해주세요."
    )
    private String phone;


    /** 사용자 역할 (STUDENT or INSTRUCTOR) */
    @Enumerated(EnumType.STRING)
    private MemberRole role;
}
