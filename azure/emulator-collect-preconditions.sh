#!/usr/bin/env bash

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

collect() {
    if [[ ! -z "$1" ]]; then
        PORT=$1
        SELECT="-s emulator-$1"
        APPEND="-$1"
        mkdir emulator-settings/$1
    fi

    # Collect current logcat output.
    $ANDROID_HOME/platform-tools/adb $SELECT logcat -d > emulator-settings/$PORT/initial-logcat$APPEND.txt
    $ANDROID_HOME/platform-tools/adb $SELECT logcat -c

    # Take a picture to get visual Feedback of the final state.
    $ANDROID_HOME/platform-tools/adb $SELECT exec-out screencap -p > emulator-settings/$PORT/initial-state$APPEND.png

    # Collect emulator setup details
    cp ~/.android/avd/android-ci$APPEND.ini emulator-settings/$PORT/emulator$APPEND.ini
    cp ~/.android/avd/android-ci$APPEND.avd/config.ini emulator-settings/$PORT/emulator-config$APPEND.ini
}

source azure/function-parallel-command.sh

mkdir emulator-settings

# Collect hardware details for deeper analysis.
sysctl hw > emulator-settings/agent-hardware.txt

if [[ -z "$@" ]]; then
    collect
else
    runParallelForEachValue collect $@
fi