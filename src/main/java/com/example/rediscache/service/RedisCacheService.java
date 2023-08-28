package com.example.rediscache.service;

import com.example.rediscache.model.CheckResult;

public interface RedisCacheService {
    CheckResult saveCheckResult(String sessionId, CheckResult checkResult);

    CheckResult getCheckResultIfExists(String sessionId);
}
