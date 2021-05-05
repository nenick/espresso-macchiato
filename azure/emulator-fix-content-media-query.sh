#!/usr/bin/env bash

# Sometimes it doesn't find anything, but it is there and next calls will return it ...

fixContentMediaAndroid() {
    if [[ ! -z "$1" ]]; then
        SELECT="-s emulator-$1"
    fi

    echo "Make Android system writable"
    $ANDROID_HOME/platform-tools/adb $SELECT shell mount -o rw,remount /system

    echo "Rename origin content command"
    $ANDROID_HOME/platform-tools/adb $SELECT shell mv /system/bin/content /system/bin/content-origin

    echo "Create script to stabilize content media query"
    # For our use cases its just enough to repeat it a few times until we have the Android directory reported again.
    $ANDROID_HOME/platform-tools/adb $SELECT shell echo 'while true\; do' \> /system/bin/content
    $ANDROID_HOME/platform-tools/adb $SELECT shell echo '    CONTENT=\`/system/bin/content-origin \"\$@\"\`' \>\> /system/bin/content
    $ANDROID_HOME/platform-tools/adb $SELECT shell echo '    COUNT=\`echo \"\$CONTENT\" \| grep -c Android\`' \>\> /system/bin/content
    $ANDROID_HOME/platform-tools/adb $SELECT shell echo '    if [[ \$COUNT -gt 0 ]]\; then' \>\> /system/bin/content
    $ANDROID_HOME/platform-tools/adb $SELECT shell echo '        echo \$CONTENT' \>\> /system/bin/content
    $ANDROID_HOME/platform-tools/adb $SELECT shell echo '        break' \>\> /system/bin/content
    $ANDROID_HOME/platform-tools/adb $SELECT shell echo '    fi' \>\> /system/bin/content
    $ANDROID_HOME/platform-tools/adb $SELECT shell echo '    echo \"next try ...\"' \>\> /system/bin/content
    $ANDROID_HOME/platform-tools/adb $SELECT shell echo '    sleep 1' \>\> /system/bin/content
    $ANDROID_HOME/platform-tools/adb $SELECT shell echo 'done' \>\> /system/bin/content

    #$ANDROID_HOME/platform-tools/adb $SELECT shell echo '/system/bin/content-origin \"\$@\"\; /system/bin/content-origin \"\$@\"\; /system/bin/content-origin \"\$@\"' \> /system/bin/content
    $ANDROID_HOME/platform-tools/adb $SELECT shell chmod 777 /system/bin/content
}

ANDROID_VERSION=$1
shift

echo "Check if fixes for content are necessary ..."
# Android 16 don't has grep command
if [[ "19" == *"$ANDROID_VERSION"* ]]; then
    echo "Apply fix for content media no Android found"
else
    echo "Emulator should work fine"
    exit
fi

if [[ -z "$@" ]]; then
    fixContentMediaAndroid
else
    for PORT in $@; do
        fixContentMediaAndroid $PORT
    done
fi