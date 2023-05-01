package com.denimdingo.tikitaka.controller;

import com.denimdingo.tikitaka.RedisService;
import com.denimdingo.tikitaka.dto.AuthReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final RedisService redisService;

    /**
     * Sign-up processing function
     *
     * @param data 로그인 요청 DTO
     */
    @PostMapping(value = "/sign-up")
    public void signUp(@RequestBody AuthReq.SignUp data) {
        System.out.println("Id = " + data.getId());
        System.out.println("Password = " + data.getPassword());
        System.out.println("Nickname = " + data.getNickname());

        redisService.setRedisStringValue(data);
    }

    /**
     * Sign-in processing function
     *
     * @param data 로그인 요청 DTO
     */
    @PostMapping(value = "/sign-in")
    public void signIn(@RequestBody AuthReq.SignIn data) {
        // TODO: 유효성 검사 및 데이터 존재 유무 확인 로직 구현할 것 (Redis 로 회원 관리)
        System.out.println("Id = " + data.getId());
        System.out.println("Password = " + data.getPassword());

        // TODO: 암호화
        redisService.getRedisStringValue(data.getId());
    }
}
