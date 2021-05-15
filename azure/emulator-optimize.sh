#!/usr/bin/env bash

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

optimize() {
    if [[ ! -z "$1" ]]; then
        SELECT="-s emulator-$1"
    fi

    ################################################################################
    # Removed what could be removed, everything less gives more performance for us.
    # Too slow for the gain at the end.
    # $ANDROID_HOME/platform-tools/adb shell 'pm list packages -f' | sed -e 's/.*=//' | sed -e "s#^#$ANDROID_HOME/platform-tools/adb uninstall #" | sort | bash

    ################################################################################
    # Disable what could be disabled, everything less gives more performance for us.
    # Automatically disable all is possible but results in defect emulator ... so you have to pic some of the annoying
    # $ANDROID_HOME/platform-tools/adb shell 'pm list packages -f' | sed -e 's/.*=//' | sed -e "s#\(.*\)#$ANDROID_HOME/platform-tools/adb shell 'su root pm disable \1'#" | sort | awk '{system($0)}'

    ################################################################################
    # Kill wait could be killed, everything less gives more performance for us.
    # Too slow for the gain at the end.
    # $ANDROID_HOME/platform-tools/adb shell ps | grep -v PID | awk '{print $2}' | xargs $ANDROID_HOME/platform-tools/adb shell kill 2> /dev/null

    ################################################################################
    # Does just restart the gms persistent package but reduce spam.
    $ANDROID_HOME/platform-tools/adb shell ps | grep com.google.android.gms.persistent | awk '{print $2}' | xargs $ANDROID_HOME/platform-tools/adb shell su root kill 2> /dev/null

}

source azure/function-parallel-command.sh

if [[ -z "$@" ]]; then
    optimize
else
    runParallelForEachValue optimize $@
fi