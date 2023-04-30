package com.denimdingo.tikitaka.controller;

import com.denimdingo.tikitaka.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SignInController {

    private final RedisService redisService;

    /**
     * Login processing function
     * @param Id 계정 아이디
     * @param password 계정 패스워드
     */
    @PostMapping(value = "/sign-in")
    public void signIn(String Id, String password) {
        // TODO: 유효성 검사 및 데이터 존재 유무 확인 로직 구현할 것 (Redis 로 회원 관리)
    }
}
