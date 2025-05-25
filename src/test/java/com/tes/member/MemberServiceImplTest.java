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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tes.global.exception.UserException;
import com.tes.member.domain.entity.Member;
import com.tes.member.domain.repository.MemberRepository;
import com.tes.member.model.request.LoginReqDTO;
import com.tes.member.model.response.LoginResDTO;
import com.tes.member.service.MemberServiceImpl;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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