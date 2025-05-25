package com.tes.member;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.tes.global.config.SecurityConfig;
import com.tes.global.exception.UserException;
import com.tes.member.controller.MemberController;
import com.tes.member.enums.MemberRole;
import com.tes.member.model.response.LoginResDTO;
import com.tes.member.service.MemberService;

@WebMvcTest(MemberController.class)
@Import(SecurityConfig.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
	@MockBean
    private MemberService memberService;
    
    @Test
    @DisplayName("로그인 성공")
    void testLoginSuccess() throws Exception {
        // given
        String generation = "3기";
        String name = "홍길동";
        String password = "Password1!";

        LoginResDTO loginResDTO = LoginResDTO.builder()
                .id(1L)
                .name(name)
                .role(MemberRole.STUDENT)
                .build();

        BDDMockito.given(memberService.login(any()))
                .willReturn(loginResDTO);
        
        MockHttpSession session = new MockHttpSession();

        // when & then
        mockMvc.perform(post("/login")
                        .param("generation", generation)
                        .param("name", name)
                        .param("password", password)
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/subject/dashboard"));

        // 세션에 로그인 정보가 저장되었는지 검증
        Object memberAttr = session.getAttribute("member");
        assert memberAttr instanceof LoginResDTO;
        LoginResDTO savedMember = (LoginResDTO) memberAttr;
        assert savedMember.getId() == 1;
        assert savedMember.getName().equals(name);
        assert savedMember.getRole() == MemberRole.STUDENT;
    }
    
    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    void testLoginFail_userNotFound() throws Exception {
        // given
        BDDMockito.given(memberService.login(any()))
        .willThrow(new UserException("아이디가 존재하지 않습니다.", "pages/login"));
        
        // when & then
        mockMvc.perform(post("/login")
                        .param("generation", "1")
                        .param("name", "없음")
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/login"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @DisplayName("로그인 실패 - 기수(generation) 빈 값")
    void testLoginFail_generationBlank() throws Exception {
        mockMvc.perform(post("/login")
                        .param("generation", "")
                        .param("name", "홍길동")
                        .param("password", "Password1!"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/login"))
                .andExpect(model().attributeExists("errorMessage"));
    }
    
    @Test
    @DisplayName("로그인 실패 - 이름(name)이 한글이 아님")
    void testLoginFail_nameNotKorean() throws Exception {
        mockMvc.perform(post("/login")
                        .param("generation", "3기")
                        .param("name", "John123")
                        .param("password", "Password1!"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/login"))
                .andExpect(model().attributeExists("errorMessage"));
    }
    
    @Test
    @DisplayName("로그인 실패 - 비밀번호(password) 조건 불충족")
    void testLoginFail_passwordInvalid() throws Exception {
        mockMvc.perform(post("/login")
                        .param("generation", "3기")
                        .param("name", "홍길동")
                        .param("password", "abc123")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("pages/login"))
                .andExpect(model().attributeExists("errorMessage"));
    }
    
    @Test
    @DisplayName("로그인 실패 - 모든 필드 유효성 오류")
    void testLoginFail_allInvalid() throws Exception {
        mockMvc.perform(post("/login")
                        .param("generation", "")
                        .param("name", "1234")
                        .param("password", "123"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/login"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}