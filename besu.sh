#!/usr/bin/env bash
set -e

BESU_PATH=${ENV_BESU_PATH:-"besu"}

DIR="$PWD"
DATA_DIR="$DIR/out"
BESU_DATA="$DATA_DIR/besu"
PLUGIN_DIR="$DIR/build/libs/"

export BESU_OPTS=${ENV_BESU_OPTS:-"-Dbesu.plugins.dir=$PLUGIN_DIR"}
echo "Starting Besu with Redis storage plugin."
$BESU_PATH "$1" \
--logging=debug \
--data-path="$BESU_DATA" --rpc-http-cors-origins=all --discovery-enabled=false --rpc-http-enabled --network=dev --rpc-http-apis=ETH,NET,WEB3,DEBUG,ADMIN,TRACE,PLUGINS