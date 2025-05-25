package com.tes.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tes.global.exception.UserException;
import com.tes.member.domain.entity.Member;
import com.tes.member.domain.repository.MemberRepository;
import com.tes.member.model.request.LoginReqDTO;
import com.tes.member.model.response.LoginResDTO;
import com.tes.member.service.MemberServiceImpl;

/**
 * {@link com.tes.member.service.MemberServiceImpl} 클래스의 단위 테스트를 수행합니다.
 *
 * <p>
 * 사용자 로그인 기능의 흐름을 검증하며, 다음 시나리오를 포함합니다:
 * <ul>
 *     <li>로그인 성공 시 사용자 정보를 반환하는지</li>
 *     <li>존재하지 않는 사용자일 경우 예외를 던지는지</li>
 *     <li>비밀번호가 일치하지 않을 경우 예외를 던지는지</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>중요:</strong> 본 테스트에서는 외부 의존성인 {@link MemberRepository}와 {@link PasswordEncoder}를 Mock 처리하여,
 * 서비스 로직의 분기 및 예외 흐름을 검증하는 데 초점을 둡니다.
 * </p>
 *
 * @see com.tes.member.service.MemberServiceImpl
 * @see com.tes.member.model.request.LoginReqDTO
 * @see com.tes.member.model.response.LoginResDTO
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberServiceImpl memberService;
    
    public static final String TEST_GENERATION = "3기";
    public static final String TEST_NAME = "홍길동";
    public static final String TEST_PASSWORD = "qwer1234!";
    public static final String TEST_ENCODE_PASSWORD = "$2a$10$u.6YeYJGWpRYg8Y.lcCrzOdz1U.D3oV1PFAn9BBzvfK/ikZWLSP9G";

    /**
     * 로그인 성공 시 {@link LoginResDTO}가 정상 반환되는지 검증합니다.
     * 사용자 정보가 일치하고 비밀번호가 유효할 때,
     * 서비스는 사용자 응답 객체를 반환해야 합니다.
     */
    @Test
    @DisplayName("로그인 성공")
    void testLoginSuccess() {
        // given
        LoginReqDTO req = new LoginReqDTO();
        req.setGeneration(TEST_GENERATION);
        req.setName(TEST_NAME);
        req.setPassword(TEST_PASSWORD);

        Member mockMember = Member.builder()
                .memberId(1L)
                .generation(TEST_GENERATION)
                .name(TEST_NAME)
                .password(TEST_ENCODE_PASSWORD)
                .build();

        when(userRepository.findByGenerationAndName(TEST_GENERATION, TEST_NAME))
                .thenReturn(Optional.of(mockMember));
        when(passwordEncoder.matches(TEST_PASSWORD, TEST_ENCODE_PASSWORD))
                .thenReturn(true);

        // when
        LoginResDTO result = memberService.login(req);

        // then
        assertNotNull(result);
        assertEquals(TEST_NAME, result.getName());
    }

    /**
     * 사용자 조회 결과가 존재하지 않는 경우,
     * {@link UserException}이 발생하는지 확인합니다.
     */
    @Test
    @DisplayName("존재하지 않는 사용자 예외")
    void testLogin_UserNotFound() {
        // given
        LoginReqDTO req = new LoginReqDTO();
        req.setGeneration(TEST_GENERATION);
        req.setName(TEST_NAME);
        req.setPassword(TEST_PASSWORD);

        when(userRepository.findByGenerationAndName(TEST_GENERATION, TEST_NAME))
                .thenReturn(Optional.empty());

        // then
        UserException exception = assertThrows(UserException.class, () -> {
            memberService.login(req);
        });

        assertEquals("아이디가 존재하지 않습니다.", exception.getMessage());
    }

    /**
     * 비밀번호가 일치하지 않는 경우,
     * {@link UserException}이 발생하는지 검증합니다.
     */
    @Test
    @DisplayName("비밀번호 불일치 예외")
    void testLogin_InvalidPassword() {
        // given
        LoginReqDTO req = new LoginReqDTO();
        req.setGeneration(TEST_GENERATION);
        req.setName(TEST_NAME);
        req.setPassword(TEST_PASSWORD);

        Member mockMember = Member.builder()
                .memberId(1L)
                .generation(TEST_GENERATION)
                .name(TEST_NAME)
                .password(TEST_ENCODE_PASSWORD)
                .build();

        when(userRepository.findByGenerationAndName(TEST_GENERATION, TEST_NAME))
                .thenReturn(Optional.of(mockMember));

        when(passwordEncoder.matches(eq(TEST_PASSWORD), anyString()))
                .thenReturn(false);

        // when & then
        UserException exception = assertThrows(UserException.class, () -> {
            memberService.login(req);
        });

        assertEquals("비밀번호가 틀렸습니다.", exception.getMessage());
    }
}