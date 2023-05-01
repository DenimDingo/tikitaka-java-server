package com.denimdingo.tikitaka.dto;

import lombok.Getter;

import java.time.LocalDateTime;

public class AuthReq {

    enum Authority {
        admin,
        normal
    }

    @Getter
    public static class SignUp {
        String          id;
        String          password;
        String          nickname;
        String          profileImg;
        Authority       authority;
        LocalDateTime   createTime;
    }

    @Getter
    public static class SignIn {
        String id;
        String password;
    }
}
