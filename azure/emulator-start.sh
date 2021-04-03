#!/usr/bin/env bash

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

########################################################################################
# Start emulator
#
# For default setup just call
#
#     emulator-start.sh
#     -> emulator name: android-ci
#
# You can provide additional emulator arguments through script arguments. First argument
# will be taken as part of emulator name. Make it an empty argument to take default
# emulator name e.g.
#
#     emulator-start.sh "" -additional-prop
#     -> emulator name: android-ci
#
# or
#
#     emulator-start.sh 5554 -additional-prop
#     -> emulator name: android-ci-5554
#
########################################################################################
# Why those properties?
#
# -no-snapshot
#     We don't need snapshot starts, it's mainly for ci environment, so skip it.
# -no-audio
#     We don't need sound, is just annoying, so skip it.
# -no-boot-anim
#     Improves startup time.
# -writable-system
#     For early android versions we have to fix some stuff inside the /system directory.
#     Without this flag the system will change back to read only after remounting.
#
# Why no other properties?
# a) we haven't tried them
# b) not useful for local usage e.g. with -no-window you wouldn't see anything
# c) had no useful effect yet
# d) will be set by emulator-start-on-ci.sh
#
# https://developer.android.com/studio/run/emulator-commandline
$ANDROID_HOME/emulator/emulator -avd android-ci$@ -no-snapshot -no-audio -no-boot-anim -writable-system