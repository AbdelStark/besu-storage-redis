package tech.pegasys.plus.plugin.redis.core;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.ByteArrayCodec;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hyperledger.besu.plugin.services.exception.StorageException;
import org.hyperledger.besu.plugin.services.storage.KeyValueStorage;
import org.hyperledger.besu.plugin.services.storage.KeyValueStorageTransaction;
import tech.pegasys.plus.plugin.redis.config.RedisStorageOptions;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class RedisKeyValueStorage implements KeyValueStorage {
  private static Logger LOG = LogManager.getLogger();

  private final RedisClient redisClient;
  private StatefulRedisConnection<byte[], byte[]> connection;
  private RedisCommands<byte[], byte[]> commands;

  public RedisKeyValueStorage(final RedisClient redisClient) {
    this.redisClient = redisClient;
    this.connect();
  }

  private void connect() {
    connection = redisClient.connect(new ByteArrayCodec());
    commands = connection.sync();
    /*final List<byte[]> keys = commands.scan().getKeys();
    keys.forEach(key -> LOG.info("Key: {}", Hex.encodeHexString(key)));*/
    LOG.info("Successfully connected to redis.");
  }

  public static RedisKeyValueStorage fromConfig(
      final Integer database, final RedisStorageOptions config) {
    return new RedisKeyValueStorage(
        RedisClient.create(
            RedisURI.Builder.redis(config.getHost(), config.getPort())
                .withDatabase(database)
                .build()));
  }

  @Override
  public void clear() throws StorageException {}

  @Override
  public boolean containsKey(final byte[] key) throws StorageException {
    return get(key).isPresent();
  }

  @Override
  public Optional<byte[]> get(final byte[] key) throws StorageException {
    return Optional.ofNullable(commands.get(key));
  }

  @Override
  public long removeAllKeysUnless(final Predicate<byte[]> retainCondition) throws StorageException {
    return 0;
  }

  @Override
  public Set<byte[]> getAllKeysThat(final Predicate<byte[]> returnCondition) {
    return null;
  }

  @Override
  public KeyValueStorageTransaction startTransaction() throws StorageException {
    return new RedisTransaction(commands);
  }

  @Override
  public void close() {
    LOG.info("Shutting down redis connection.");
    connection.close();
    redisClient.shutdown();
  }
}
