#!/usr/bin/env bash

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

# https://developer.android.com/studio/run/emulator-commandline
$ANDROID_HOME/emulator/emulator -avd android-ci$@ -no-snapshot -no-audio -no-boot-anim