#!/usr/bin/env bash

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

log() {
    if [[ ! -z "$1" ]]; then
        SELECT="-s emulator-$1"
    fi

    $ANDROID_HOME/platform-tools/adb $SELECT logcat "*:S" "TestRunner:V" | grep --line-buffered "started:"
}

if [[ -z "$@" ]]; then
    log &
else
    for PORT in $@; do
        log $PORT &
    done
fi