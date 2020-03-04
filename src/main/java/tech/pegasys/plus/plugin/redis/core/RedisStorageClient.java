package tech.pegasys.plus.plugin.redis.core;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class RedisStorageClient {
  public static final String DEFAULT_URI = "redis://localhost:6379/0";
  @Builder.Default private String uri = DEFAULT_URI;
}
