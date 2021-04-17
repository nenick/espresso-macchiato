#!/usr/bin/env bash

# Must exist before we start the test run or gradle tools will never find the test_data path.

fixTestDataDownload() {
    ANDROID_VERSION=$1
    if [[ ! -z "$2" ]]; then
        PORT=$2
        SELECT="-s emulator-$2"
    fi

    $ANDROID_HOME/platform-tools/adb $SELECT shell content query --uri content://media/external/file --projection _data

    # Usually it will appear after a short period. If not the emulator is perhaps not properly configured.
    TRY=0
    while true; do
        MEDIA_STORAGE=`$ANDROID_HOME/platform-tools/adb $SELECT shell content query --uri content://media/external/file --projection _data --where "_data\ LIKE\ \'%Android\'"`
        echo "$MEDIA_STORAGE"

        if [[ "$MEDIA_STORAGE" =~ .*Android ]]; then
            break
        fi

        if [ $TRY -lt 20 ]; then
            TRY=$(($TRY+1))
            sleep 3
        else
            break
        fi
    done

    if [[ ! "$MEDIA_STORAGE" =~ .*Android ]]; then
        echo "Android data directory not found, try to create it explicitly"

        if [[ "16" == *"$ANDROID_VERSION"* ]]; then
            # Never appears until any app creates some content.
            $ANDROID_HOME/platform-tools/adb $SELECT shell content insert --uri content://media/external/file --bind _data:s:/mnt/sdcard/Android
        fi

        if [[ "18" == *"$ANDROID_VERSION"* ]]; then
            # Never appears until any app creates some content.
            $ANDROID_HOME/platform-tools/adb $SELECT shell content insert --uri content://media/external/file --bind _data:s:/storage/sdcard/Android
        fi

        if [[ "30" == *"$ANDROID_VERSION"* ]]; then
            # Would appear but takes too much time, so we shorten it a bit.
            $ANDROID_HOME/platform-tools/adb $SELECT shell content insert --uri content://media/external/file --bind _data:s:/storage/emulated/0/Android
        fi
    fi

    $ANDROID_HOME/platform-tools/adb $SELECT shell content query --uri content://media/external/file --projection _data
}

ANDROID_VERSION=$1
shift

if [[ -z "$@" ]]; then
    fixTestDataDownload $ANDROID_VERSION
else
    for PORT in $@; do
        fixTestDataDownload $ANDROID_VERSION $PORT
    done
fi