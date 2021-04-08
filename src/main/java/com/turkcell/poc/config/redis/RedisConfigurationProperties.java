package com.turkcell.poc.config.redis;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="redis")
public class RedisConfigurationProperties {

  Map<String, Long> expirations = new HashMap<String, Long>();
  private long defaultTimeout;
  private int port;
  private String host;

  public Map<String, Long> getExpirations() {
    return expirations;
  }

  public void setExpirations(Map<String, Long> expirations) {
    this.expirations = expirations;
  }

  public long getDefaultTimeout() {
    return defaultTimeout;
  }

  public void setDefaultTimeout(long defaultTimeout) {
    this.defaultTimeout = defaultTimeout;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }
}
