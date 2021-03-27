#!/usr/bin/env bash

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

# Not: Use new command-line tools, other tool locations are marked as deprecated https://developer.android.com/studio/releases/sdk-tools
# Install through Android Studio: Tools > SDK Manager > SDK Tools > Android Command-line SDK Tools
# Or download them directly from from https://developer.android.com/studio#command-tools

# Note: Get overview for available devices
# $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager list

create() {
    ANDROID_VERSION=$1

    if [[ ! -z "$2" ]]; then
        PORT=$2
        SELECT="-s emulator-$2"
        APPEND="-$2"
    fi

    echo "no" | $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd -n android-ci$APPEND -k "system-images;android-$ANDROID_VERSION;default;x86_64" -c 512M --force

    # https://stuff.mit.edu/afs/sipb/project/android/docs/tools/devices/managing-avds-cmdline.html
    # https://android.googlesource.com/platform/prebuilts/android-emulator/+/master/linux-x86_64/lib/hardware-properties.ini

    # Minimal screen size to have successful running tests.
    #sed -i "" "s/hw.lcd.density=.*/hw.lcd.density=120/g" ~/.android/avd/android-ci$APPEND.avd/config.ini
    #sed -i "" "s/hw.lcd.height=.*/hw.lcd.height=480/g" ~/.android/avd/android-ci$APPEND.avd/config.ini
    #sed -i "" "s/hw.lcd.width=.*/hw.lcd.width=320/g" ~/.android/avd/android-ci$APPEND.avd/config.ini

    # Disable all extra stuff e.g. sensors.
    sed -i "" "s/yes/no/g" ~/.android/avd/android-ci$APPEND.avd/config.ini
    # GPU should still be available for performance.
    sed -i "" "s/hw.gpu.enabled=.*/hw.gpu.enabled=yes/g" ~/.android/avd/android-ci$APPEND.avd/config.ini
    # Avoid opening soft keyboard for performance and less flakiness.
    #sed -i "" "s/hw.keyboard=.*/hw.keyboard=yes/g" ~/.android/avd/android-ci$APPEND.avd/config.ini
    # disable on screen android navigation buttons.
    sed -i "" "s/hw.mainKeys=.*/hw.mainKeys=yes/g" ~/.android/avd/android-ci$APPEND.avd/config.ini

    # For tests it should be possible to run completely offline.
    sed -i "" "s/hw.gsmModem=.*/hw.gsmModem=no/g" ~/.android/avd/android-ci$APPEND.avd/config.ini

    # Some memory, hope it help to have a bit more.
    sed -i "" "s/hw.ramSize=.*/hw.ramSize=512M/g" ~/.android/avd/android-ci$APPEND.avd/config.ini
    sed -i "" "s/vm.heapSize=.*/vm.heapSize=64M/g" ~/.android/avd/android-ci$APPEND.avd/config.ini
}

ANDROID_VERSION=$1
shift

echo "y" | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --install "system-images;android-$ANDROID_VERSION;default;x86_64"

if [[ -z "$@" ]]; then
    create $ANDROID_VERSION
else
    for PORT in $@; do
        create $ANDROID_VERSION $PORT
    done
fi