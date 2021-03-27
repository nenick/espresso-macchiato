#!/usr/bin/env bash

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

waitForDevice() {
    if [[ ! -z "$1" ]]; then
        SELECT="-s emulator-$1"
    fi

    # wait for device
    $ANDROID_HOME/platform-tools/adb $SELECT wait-for-device shell 'while [[ -z $(getprop sys.boot_completed | tr -d '\r') ]]; do echo "emulator '$1' is starting ..." && sleep 5; done; input keyevent 82'
}

source azure/function-parallel-command.sh

if [[ -z "$@" ]]; then
    waitForDevice
else
    runParallelForEachValue waitForDevice $@
fi

$ANDROID_HOME/platform-tools/adb devices

# I call it magic time, give it to the emulator and he will be lucky until the end of his short life time
sleep 10