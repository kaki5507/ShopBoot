package com.shop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberFormDto { // 가입정보 담는 DTO

    @NotBlank(message = "이름은 필수 입력 값.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "이메일은 필수 입력 값.")
    @Length(min = 8, max = 16, message = "비밀번호 8자 이상 , 16자 이하")
    private String password;

    @NotEmpty(message = "주소 필수 입력 값.")
    private String address;

}
