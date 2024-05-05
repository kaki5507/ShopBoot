package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto { // 가입정보 담는 DTO
    private String name;
    private String email;
    private String password;
    private String address;
}
