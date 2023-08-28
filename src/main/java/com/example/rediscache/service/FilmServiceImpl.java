package com.example.rediscache.service;

import com.example.rediscache.client.SwapiClient;
import com.example.rediscache.model.CheckResult;
import com.example.rediscache.model.Films;
import org.redisson.client.RedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FilmServiceImpl implements FilmService {

    private final Logger log = LoggerFactory.getLogger(FilmServiceImpl.class);

    private final RedisCacheService redisCacheService;

    private final SwapiClient swapiClient;


    public FilmServiceImpl(RedisCacheService redisCacheService,
                           SwapiClient swapiClient) {
        this.redisCacheService = redisCacheService;
        this.swapiClient = swapiClient;
    }

    private String getSessionId() {
        return UUID.randomUUID().toString();
    }

    public CheckResult check() {
        /**
         * Some business logic goes here
         */
        CheckResult checkResult = new CheckResult();
        checkResult.setResult(true);
        checkResult.setFailedCheck("my-prerequisite-check");
        checkResult.setMessage("My prerequisite check was successful !");

        /**
         * ---=== WRITE CACHE ===---
         * Now that we have result we can save it into Redis cache
         */
        String sessionId = getSessionId();
        try {
            redisCacheService.saveCheckResult(sessionId, checkResult);
        } catch (RedisException e) {
            log.error("Got redis exception with message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("Written into Redis cache: {}={}", sessionId, checkResult);

        /**
         * ---=== READ CACHE ===---
         * Ensure the result exists
         */
        CheckResult checkResultExisting;
        try {
            checkResultExisting = redisCacheService.getCheckResultIfExists(sessionId);
        } catch (RedisException e) {
            log.error("Got redis exception with message: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        if (checkResultExisting == null) {
            log.error("CACHE EMPTY FOR: {}", sessionId);
        } else {
            log.info("Read from Redis cache: {}={}", sessionId, checkResultExisting);
        }

        return checkResultExisting;
    }

    @Cacheable(value = "filmData", key = "'films'")
    public Films getFilms() {
        log.info("Going to call external Swapi now!!!");
        Films result = swapiClient.getFilms();
        return result;
    }

}
