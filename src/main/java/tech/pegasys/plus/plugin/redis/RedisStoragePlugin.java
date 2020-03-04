package tech.pegasys.plus.plugin.redis;

import com.google.auto.service.AutoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hyperledger.besu.plugin.BesuContext;
import org.hyperledger.besu.plugin.BesuPlugin;
import org.hyperledger.besu.plugin.services.PicoCLIOptions;
import picocli.CommandLine;
import tech.pegasys.plus.plugin.redis.core.RedisStorageClient;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

// The AutoService annotation (when paired with the corresponding annotation processor) will
// automatically handle adding the relevant META-INF files so Besu will load this plugin.
@AutoService(BesuPlugin.class)
public class RedisStoragePlugin implements BesuPlugin {
  private static Logger LOG = LogManager.getLogger();
  private static String PLUGIN_NAME = "redis-storage";

  @CommandLine.Option(names = "--plugin-redis-storage-uri")
  public String redisURI = RedisStorageClient.DEFAULT_URI;

  @Override
  public void register(final BesuContext context) {
    LOG.info("Registering plugin {}.", PLUGIN_NAME);
    context
        .getService(PicoCLIOptions.class)
        .ifPresentOrElse(
            this::handleCLIOptions, () -> LOG.error("Could not obtain PicoCLIOptions service."));
  }

  private void handleCLIOptions(final PicoCLIOptions picoCLIOptions) {
    picoCLIOptions.addPicoCLIOptions(PLUGIN_NAME, this);
  }

  @Override
  public void start() {
    LOG.info("Starting plugin {}.", PLUGIN_NAME);
    final RedisStorageClient redisStorageClient =
        RedisStorageClient.builder().uri(redisURI).build();
    LOG.info(redisStorageClient.toString());
  }

  @Override
  public CompletableFuture<Void> reloadConfiguration() {
    LOG.warn("Configuration reloaded is not supported");
    return CompletableFuture.completedFuture(null);
  }

  @Override
  public void stop() {
    LOG.info("Stopping plugin {}.", PLUGIN_NAME);
  }

  @Override
  public Optional<String> getName() {
    return Optional.of(PLUGIN_NAME);
  }
}
