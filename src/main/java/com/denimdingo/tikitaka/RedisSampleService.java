package com.denimdingo.tikitaka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisSampleService {

    private final StringRedisTemplate stringRedisTemplate;
    private static final Logger redisLogger = LoggerFactory.getLogger(RedisSampleService.class);

    /**
     * Get data value in Redis database
     * @param key data 키
     */
    public void getRedisStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        redisLogger.info("Redis key   : {}", key);
        redisLogger.info("Redis value : {}", stringValueOperations.get(key));
    }

    /**
     * Set data in Redis
     * @param key data 키
     * @param value data 값
     */
    public void setRedisStringValue(String key, String value) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(key, value);
        redisLogger.info("Redis key   : {}", key);
        redisLogger.info("Redis value : {}", stringValueOperations.get(key));
    }
}
