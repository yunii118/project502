package org.choongang.member.controllers;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.choongang.member.constants.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class RequestJoin {

    private String gid = UUID.randomUUID().toString();

    private String MTYPE = "M"; // 기본값 M 일반회원, F 농장회원

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String userId;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    private String tel;

    private String gender = Gender.FEMAIL.name();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;// 생년월일

    @NotBlank
    private String farmTitle;// 농장이름

    @NotBlank
    private String zoneCode; // 우편번호

    @NotBlank
    private String address; // 주소

    private String addressSub; // 나머지주소


    @AssertTrue
    private boolean agree;


}
