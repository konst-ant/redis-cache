package com.example.rediscache.service;

import com.example.rediscache.model.CheckResult;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    @CachePut(value = "checkResult", key = "#p0")
    public CheckResult saveCheckResult(String sessionId, CheckResult checkResult) {
        return checkResult;
    }

    @Cacheable(value = "checkResult", key = "#p0", unless = "#result == null")
    public CheckResult getCheckResultIfExists(String sessionId) {
        return null;
    }

}
