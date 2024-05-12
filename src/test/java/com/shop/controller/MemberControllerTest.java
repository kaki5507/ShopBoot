package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(String email , String password){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("김이름씨");
        memberFormDto.setAddress("주소 조말론");
        memberFormDto.setPassword(password); // 파라미터로 받은 패스워드로
        Member member = Member.createMember(memberFormDto, passwordEncoder); // 암호화된 비밀번호
        return memberService.saveMember(member); // 서비스에 중복된 가입 확인
    }

    @Test
    @DisplayName("Mock 가짜 로그인 성공 테스트")
    public void MockLoginTest() throws Exception{
        String email = "test@naver.com";
        String password = "1234";
        this.createMember(email, password);
        ResultActions resultActions =  mockMvc.perform(MockMvcRequestBuilders.post("/members/login")
                .param("email" , email)
                .param("password", password));

        resultActions.andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("Mock 가짜 로그인 실패 성공 테스트")
    public void MockLoginFaiTest() throws Exception{
        String email = "test@naver.com";
        String password = "1234";
        this.createMember(email, password);
        ResultActions resultActions =  mockMvc.perform(MockMvcRequestBuilders.post("/members/login")
                .param("email" , email)
                .param("password", "12345"));

        resultActions.andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

}
