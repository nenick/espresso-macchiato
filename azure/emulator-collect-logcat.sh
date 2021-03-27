#!/usr/bin/env bash

# Filter to analyse annoying or unexpected log entries
# grep -v "\(TestRunner\|View\|ActivityManager\|ApingUnauthorizedErrorSubscriber\|KeycloakErrorSubscriber\|LifecycleMonitor\|TutorialDialogFragment\|MonitoringInstr\|WindowManager\|MockWebServer\)" ~/Downloads/emulator-logcat-5556\ \(1\).txt > logcat.log

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

collect() {
    if [[ ! -z "$1" ]]; then
        PORT=$1
        SELECT="-s emulator-$1"
        APPEND="-$1"
        mkdir emulator-logs/$1
    fi

    nohup $ANDROID_HOME/platform-tools/adb $SELECT logcat > emulator-logs/$PORT/emulator-logcat$APPEND.txt
}

mkdir emulator-logs

if [[ -z "$@" ]]; then
    collect &
else
    for PORT in $@; do
       collect $PORT &
    done
fi