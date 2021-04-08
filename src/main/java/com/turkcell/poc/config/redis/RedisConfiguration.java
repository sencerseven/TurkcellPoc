package com.turkcell.poc.config.redis;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@EnableCaching
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {

  @Autowired
  RedisConfigurationProperties cacheProperties;

  private static RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(timeoutInSeconds));
  }


  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(cacheProperties.getHost());
    redisStandaloneConfiguration.setPort(cacheProperties.getPort());
    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate redisTemplate() {
    RedisTemplate redisTemplate = new RedisTemplate();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    return redisTemplate;
  }


  @Bean
  public RedisCacheConfiguration cacheConfiguration(RedisConfigurationProperties properties) {
    return createCacheConfiguration(properties.getDefaultTimeout());
  }

  @Bean
  public CacheManager cacheManager(
      RedisConnectionFactory redisConnectionFactory, RedisConfigurationProperties properties) {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
    for (Map.Entry<String, Long> cacheNameAndTimeout : properties.getExpirations().entrySet()) {
      cacheConfigurations.put(
          cacheNameAndTimeout.getKey(), createCacheConfiguration(cacheNameAndTimeout.getValue()));
    }

    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(cacheConfiguration(properties))
        .withInitialCacheConfigurations(cacheConfigurations)
        .build();
  }
}
