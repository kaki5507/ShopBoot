package com.shop.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> { // 수정일 등록자 자동 입력
    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = "";
        if(authentication != null){ // 현재 로그인 사용자 정보 조회하여 등록자 , 수정자 정함
            userId = authentication.getName();
        }

        return Optional.of(userId);
    }
}
