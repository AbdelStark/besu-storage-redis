package tech.pegasys.plus.plugin.redis.core;

import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import org.hyperledger.besu.plugin.services.exception.StorageException;
import org.hyperledger.besu.plugin.services.storage.KeyValueStorageTransaction;

@RequiredArgsConstructor
public class RedisTransaction implements KeyValueStorageTransaction {
  private final RedisCommands<byte[], byte[]> commands;

  @Override
  public void put(byte[] key, byte[] value) {
    commands.set(key, value);
  }

  @Override
  public void remove(byte[] key) {
    commands.del(key);
  }

  @Override
  public void commit() throws StorageException {}

  @Override
  public void rollback() {}
}
