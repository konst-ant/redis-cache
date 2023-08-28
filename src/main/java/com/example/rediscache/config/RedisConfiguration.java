package com.example.rediscache.config;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;


@Configuration
public class RedisConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    @Value("${application.cache.ttl}")
    private int ttl;

    @Value("${application.cache.max-idle-time}")
    private long maxIdleTime;

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(@Value("classpath:/config/redisson.yml") Resource configFile, ObjectMapper mapper) throws IOException {
        Config config = Config.fromYAML(configFile.getInputStream());
        config.setCodec(new JsonJacksonCodec(mapper));
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();

        config.put("checkResult", new CacheConfig(
                        TimeUnit.SECONDS.toMillis(ttl),
                        TimeUnit.SECONDS.toMillis(maxIdleTime)
                )
        );
        config.put("filmData", new CacheConfig(
                        TimeUnit.SECONDS.toMillis(ttl),
                        TimeUnit.SECONDS.toMillis(maxIdleTime)
                )
        );
        return new RedissonSpringCacheManager(redissonClient, config);
    }
}
