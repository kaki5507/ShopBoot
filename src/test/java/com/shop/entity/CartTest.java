package com.shop.entity;

import com.shop.dto.MemberFormDto;
import com.shop.repository.CartRepository;
import com.shop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public Member createMember(){ // 회원 엔티티를 생성하는 메소드
        MemberFormDto memberFormDto = new MemberFormDto(); //가입정보 담는 폼으로 set해서 데이터 삽입
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("김테스트");
        memberFormDto.setAddress("김포 부천 그 사이");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest(){
        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart(); // 카트 생성
        cart.setMember(member); // 장바구니 주인 담음
        cartRepository.save(cart); // 기능이용할려고 save 할려고 cart를

        em.flush(); // 트랜잭션 끝날때 db반영 매니저로부터 강제 호출해서 저장하는 것임
        em.clear(); // 실제 db에서 장바구니 엔티티를 가져 올 때 회원 엔티티도 같이 가져오는지 위해서 비워줌.

        Cart savedCart = cartRepository.findById(cart.getId()) // 카트 생성해서 저장된 아이디 가져옴
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(savedCart.getMember().getId(), member.getId());

    }
}
