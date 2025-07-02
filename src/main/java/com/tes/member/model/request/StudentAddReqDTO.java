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

/**
 * 학생 등록 요청 DTO입니다.
 * <p>
 * 	학생 정보를 등록할 때 사용하는 요청 객체로,
 * 	기수, 이름, 비밀번호, 연락처, 사용자 역할 정보를 포함합니다.
 * </p>
 * 
 * @author
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class StudentAddReqDTO {
    @NotBlank(message = "기수는 필수 입력입니다.")
    @Size(max = 20, message = "기수는 20자 이내로 입력해주세요.")
	private String generation;

    @NotBlank(message = "이름은 필수 입력입니다.")
    @Size(max = 10, message = "이름은 10자 이내로 입력해주세요.")
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 입력 가능합니다.")
	private String name;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 50자 이내로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\W).{8,50}$",
             message = "비밀번호는 영문 대소문자와 특수문자를 포함해야 합니다.")
	private String password;
	
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
