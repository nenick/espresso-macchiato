#!/usr/bin/env bash

fixPullPermissionDenied() {
    if [[ ! -z "$1" ]]; then
        SELECT="-s emulator-$1"
    fi

    $ANDROID_HOME/platform-tools/adb $SELECT root remount
}

ANDROID_VERSION=$1
shift

echo "Check if fixes for permission denied are necessary ..."
if [[ "30" == *"$ANDROID_VERSION"* ]]; then
    echo "Apply fix for pull permission denied"
else
    echo "Emulator should work fine"
    exit
fi

if [[ -z "$@" ]]; then
    fixPullPermissionDenied
else
    for PORT in $@; do
        fixPullPermissionDenied $PORT
    done
fi