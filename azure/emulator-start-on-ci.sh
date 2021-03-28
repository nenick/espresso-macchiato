#!/usr/bin/env bash

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

# Debug secure settings
# http://sviatoslavdev.blogspot.com/2018/02/adb-setings-secure.html
# $ANDROID_HOME/platform-tools/adb shell settings list secure

echo "Starting emulator"

start() {
    if [[ ! -z "$1" ]]; then
        echo "Found port $1"
        PORT=$1
        SELECT="-s emulator-$1"
        APPEND="-$1"
    else
        echo "Skip port assignment"
        APPEND=" "
    fi

     nohup ./azure/emulator-start.sh "$APPEND" -accel auto -gpu swiftshader_indirect -screen no-touch -no-window
}

$ANDROID_HOME/platform-tools/adb start-server

if [[ -z "$@" ]]; then
    echo "Use single emulator"
    start &
else
    for PORT in $@; do
        echo "Start emulator $PORT"
        start $PORT &
    done
fi