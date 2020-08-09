#!/usr/bin/env bash
set -e

# BESU_PATH=${ENV_BESU_PATH:-"besu"}

DIR="$PWD"
DATA_DIR="$DIR/out"
BESU_DATA="$DATA_DIR/besu"
PLUGIN_DIR="$DIR/build/libs/"
#BESU_PATH="$DIR/besu"
export BESU_OPTS=${ENV_BESU_OPTS:-"-Dbesu.plugins.dir=$PLUGIN_DIR"}
echo "Starting Besu with Redis storage plugin."
$BESU_PATH "$1" --data-path="$BESU_DATA" --rpc-http-enabled --data-path=data --genesis-file="$DIR/"genesis.json --p2p-port=30305 --bootnodes=enode://694c9cdd1a95ea242cf194d6e0738c44379446ed59abe1a34763dd48f700a3edf3f849b5b2127d38202583ec17eecee604d15d3c7bf83a86494c26571507400e@54.226.223.85:30305
--auto-log-bloom-caching-enabled=false

#--network=goerli --nat-method=UPNP
#--network=dev --miner-enabled --miner-coinbase=0xfe3b557e8fb62b89f4916b721be55ceb828dbd73 --auto-log-bloom-caching-enabled=false