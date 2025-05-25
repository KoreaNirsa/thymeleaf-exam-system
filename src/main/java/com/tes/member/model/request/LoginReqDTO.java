package com.tes.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 로그인 요청 시 사용자 입력 값을 전달하기 위한 데이터 전송 객체(DTO)입니다.
 *
 * <p>
 * 사용자는 기수(generation), 이름(name), 비밀번호(password)를 입력하며,
 * 해당 값은 {@code @Controller}에서 폼 바인딩 후 {@code MemberService}를 통해 인증 처리에 사용됩니다.
 * </p>
 *
 * <p>유효성 검사를 위해 Jakarta Bean Validation 어노테이션이 포함되어 있으며</p>
 * <ul>
 *     <li>{@code @NotBlank} : 필수 입력값</li>
 *     <li>{@code @Size} : 입력 길이 제한</li>
 *     <li>{@code @Pattern} : 정규식 기반 유효성 검사</li>
 * </ul>
 *
 * <p>Thymeleaf의 form 바인딩을 위한 기본 생성자와 접근자(Getter/Setter)를 제공합니다.</p>
 *
 * @author 
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginReqDTO {
	 /** 기수 (예: 1기, 2기) - 필수 입력, 최대 20자 */
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
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이내로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,20}$",
             message = "비밀번호는 영문 대소문자와 특수문자를 포함해야 합니다.")
    private String password;
}
