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
        echo "Android data directory not found"

        # The Android folder must exist at start time to get additional test data download to work.
        # Versions after android 18 will have all properly prepared.
        #echo "Prepare Android data directory"
        #$ANDROID_HOME/platform-tools/adb -s emulator-5554 shell mkdir /storage/sdcard/Android

        # Usually its now enough to restart the emulator to get Android folder reported.
        #echo "Restart emulator to fix test_data download"
        #$ANDROID_HOME/platform-tools/adb $SELECT -e reboot

        #./azure/emulator-wait.sh $1

        #$ANDROID_HOME/platform-tools/adb $SELECT shell content query --uri content://media/external/file --projection _data
    fi
}



if [[ -z "$@" ]]; then
    fixTestDataDownload
else
    for PORT in $@; do
        fixTestDataDownload $PORT
    done
fi