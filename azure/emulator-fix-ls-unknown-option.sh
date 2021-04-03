#!/usr/bin/env bash

# fix for ls: Unknown option '-1'. Aborting.

fixLsUnknownOption() {
    if [[ ! -z "$1" ]]; then
        SELECT="-s emulator-$1"
    fi

    echo "Make Android system writable"
    $ANDROID_HOME/platform-tools/adb $SELECT shell mount -o rw,remount /system

    echo "Create fake ls command"
    # Origin ls command is just a link to toolbox.
    # Fake has to be named "ls" also or toolbox will not understand what we want.
    $ANDROID_HOME/platform-tools/adb $SELECT shell ln -s /system/bin/toolbox /system/xbin/ls

    echo "Create script to fix ls: Unknown option '-1'"
    # For our use cases its just enough to eliminate this argument.
    $ANDROID_HOME/platform-tools/adb $SELECT shell echo 'args=\"\$@\"\; /system/xbin/ls \${args//\\-1/}' \> /system/xbin/ls-missing-option-hack.sh
    $ANDROID_HOME/platform-tools/adb $SELECT shell chmod 777 /system/xbin/ls-missing-option-hack.sh

    echo "Switch origin ls to script"
    $ANDROID_HOME/platform-tools/adb $SELECT shell rm /system/bin/ls
    $ANDROID_HOME/platform-tools/adb $SELECT shell ln -s /system/xbin/ls-missing-option-hack.sh /system/bin/ls

    echo "Command ls now pointing to ..."
    $ANDROID_HOME/platform-tools/adb $SELECT shell /system/xbin/ls -l /system/bin/ls
}

ANDROID_VERSION=$1
shift

echo "Check if fixes for ls are necessary ..."
if [[ "19" == *"$ANDROID_VERSION"* ]]; then
    echo "Apply fix for ls: Unknown option '-1'"
else
    echo "Emulator should work fine"
    exit
fi

if [[ -z "$@" ]]; then
    fixLsUnknownOption
else
    for PORT in $@; do
        fixLsUnknownOption $PORT
    done
fi