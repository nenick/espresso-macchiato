#!/usr/bin/env bash

################################################################################
# Configure emulator variant
[ -z "${1}" ] && echo "Missing argument for android version (e.g. android-27)" && exit 1
AVD_VARIANT="system-images;${1};default;x86"
AVD_NAME=${1}

################################################################################
# Install and create emulator
echo "y" | $ANDROID_HOME/tools/bin/sdkmanager --install ${AVD_VARIANT}
echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n ${AVD_NAME} -k ${AVD_VARIANT} -c 512M --force

################################################################################
# Activate soft keyboard to perform checks on it
echo "hw.keyboard=no" >> ~/.android/avd/${AVD_NAME}.avd/config.ini
# Use more CPUs for emulation.
echo "hw.cpu.ncore=4" >> ~/.android/avd/${AVD_NAME}.avd/config.ini
# Use more Ram for emulation.
echo "hw.ramSize=1536" >> ~/.android/avd/${AVD_NAME}.avd/config.ini


################################################################################
# Print emulator config for debug purpose.
cat ~/.android/avd/${AVD_NAME}.avd/config.ini

################################################################################
# Start emulator
nohup $ANDROID_HOME/emulator/emulator -avd ${AVD_NAME} -no-snapshot -no-audio -no-window > /dev/null 2>&1 &

################################################################################
# Wait until emulator is ready
$ANDROID_HOME/platform-tools/adb wait-for-device shell 'while [[ -z $(getprop sys.boot_completed) ]]; do sleep 1; done; input keyevent 82'

################################################################################
# Give Emulator a few seconds to be really ready
sleep 10

################################################################################
# Disable animations for more speed and less flakiness on emulators.
$ANDROID_HOME/platform-tools/adb shell settings put global window_animation_scale 0
$ANDROID_HOME/platform-tools/adb shell settings put global transition_animation_scale 0
$ANDROID_HOME/platform-tools/adb shell settings put global animator_duration_scale 0

################################################################################
# Clear logs from emulator, because we want to download only test related logs.
$ANDROID_HOME/platform-tools/adb logcat -c