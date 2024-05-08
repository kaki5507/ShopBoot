package com.shop.service;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("멤버 테스트 전체")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@naver.com");
        memberFormDto.setName("킴카사디안");
        memberFormDto.setAddress("성동구 어딘가");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto,passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 TEST")
    public void saveMemberTest() {
        Member member = new Member();
        Member saveMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), saveMember.getEmail());
        assertEquals(member.getName(), saveMember.getName());
        assertEquals(member.getAddress(), saveMember.getAddress());
        assertEquals(member.getPassword(), saveMember.getPassword());
        assertEquals(member.getRole(), saveMember.getRole());
    }

    @Test
    @DisplayName("중복 테스트")
    public void saveDuplicateTest(){
        Member member1 = new Member();
        Member member2 = new Member();
        memberService.saveMember(member1);

        // 예외 던짐
        Throwable e = assertThrows(IllegalStateException.class, () -> {
           memberService.saveMember(member2);
        });

        assertEquals("이미 가입된 회원입니다." , e.getMessage());
    }
}