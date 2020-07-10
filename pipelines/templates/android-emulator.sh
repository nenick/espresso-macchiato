#!/usr/bin/env bash

# Validate arguments
[ -z "$1" ] && echo "Missing argument for android version (e.g. android-27)" && exit 1

# Configuration
EMULATOR_CONFIG="system-images;$1;default;x86"

echo "Installing emulator"

# Install AVD files
echo "y" | $ANDROID_HOME/tools/bin/sdkmanager --install ${EMULATOR_CONFIG}

echo "Starting emulator"

# Create emulator
echo "no" | $ANDROID_HOME/tools/bin/avdmanager create avd -n android_emulator -k ${EMULATOR_CONFIG} --force

# Start emulator in background
nohup $ANDROID_HOME/emulator/emulator -avd android_emulator -no-snapshot > /dev/null 2>&1 &

echo "Waiting until emulator is up"

# Wait until emulator is started
$ANDROID_HOME/platform-tools/adb wait-for-device shell 'while [[ -z $(getprop sys.boot_completed | tr -d '\r') ]]; do sleep 1; done; input keyevent 82'

$ANDROID_HOME/platform-tools/adb devices

echo "Emulator started"

# Give Emulator a few seconds to be really ready
sleep 10