# Besu Redis Key value store plugin

## Usage

Start Redis server.
Using docker compose:
```shell script
docker-compose -f ./docker/redis.yml up -d
```
Remove Redis volume:
```shell script
docker volume rm -f docker_redis_data
```

Start Besu with the redis storage plugin.
```shell script
./besu.sh --key-value-storage=redis-storage --plugin-redis-storage-host=localhost --plugin-redis-storage-port=6379
```
