package com.denimdingo.tikitaka;

import com.denimdingo.tikitaka.dto.AuthReq;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;
    private static final Logger redisLogger = LoggerFactory.getLogger(RedisService.class);

    /**
     * Get data value in Redis
     *
     * @param key data
     */
    public void getRedisStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        redisLogger.info("Redis key   : {}", key);
        redisLogger.info("Redis value : {}", stringValueOperations.get(key));
        // TODO:  예외처리
    }

    /**
     * Set data in Redis
     *
     * @param data data 키
     */
    public void setRedisStringValue(AuthReq.SignUp data) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        new Jackson2JsonRedisSerializer<>(AuthReq.SignUp.class);
        stringValueOperations.set(data.getId(), );
        redisLogger.info("Redis key   : {}", data.getId());
        redisLogger.info("Redis value : {}", stringValueOperations.get(data.getId()));
    }
}
