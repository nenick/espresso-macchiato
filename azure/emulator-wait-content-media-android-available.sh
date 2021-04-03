#!/usr/bin/env bash

fixTestDataDownload() {
    if [[ ! -z "$q" ]]; then
        PORT=$1
        SELECT="-s emulator-$1"
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

    $ANDROID_HOME/platform-tools/adb $SELECT shell content query --uri content://media/external/file --projection _data

    if [[ ! "$MEDIA_STORAGE" =~ .*Android ]]; then
        echo "Android data directory not found, create explicit media entry"
        # Necessary for android 18 that gradle will find the correct path.
        $ANDROID_HOME/platform-tools/adb shell content insert --uri content://media/external/file --bind _data:s:/storage/sdcard/Android
    fi
}



if [[ -z "$@" ]]; then
    fixTestDataDownload
else
    for PORT in $@; do
        fixTestDataDownload $PORT
    done
fi