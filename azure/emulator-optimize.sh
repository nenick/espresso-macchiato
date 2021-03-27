#!/usr/bin/env bash

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

optimize() {
    if [[ ! -z "$1" ]]; then
        SELECT="-s emulator-$1"
    fi
  
    ################################################################################
    # Stops annoying messages in logcat from useless packages
    # $ANDROID_HOME/platform-tools/adb shell "su root pm list packages"
    $ANDROID_HOME/platform-tools/adb $SELECT shell "su root pm disable com.android.dialer"
    $ANDROID_HOME/platform-tools/adb $SELECT shell "su root pm disable com.google.android.apps.messaging"
    $ANDROID_HOME/platform-tools/adb $SELECT shell "su root pm disable com.android.phone"
    $ANDROID_HOME/platform-tools/adb $SELECT shell "su root pm disable com.google.android.talk"
    $ANDROID_HOME/platform-tools/adb $SELECT shell "su root pm disable com.google.android.configupdater"
    $ANDROID_HOME/platform-tools/adb $SELECT shell "su root pm disable com.google.android.googlequicksearchbox"

    ################################################################################
    # Remove annoying stuff still active after disabling/killing.
    $ANDROID_HOME/platform-tools/adb $SELECT shell "su root pm uninstall --user 0 com.google.android.youtube"
    
    # For tests it should be possible to run completely offline but there is something hanging up ...
    #$ANDROID_HOME/platform-tools/adb shell 'svc wifi disable'
    
    # Disable keyguard completely to avoid issue logs.
    $ANDROID_HOME/platform-tools/adb $SELECT shell settings put secure lockscreen.disabled 1
    
    ################################################################################
    # Disable soft keyboard to avoid overhead of hiding it after text input.
    # https://testyour.app/blog/emulator
    $ANDROID_HOME/platform-tools/adb $SELECT shell settings put secure show_ime_with_hard_keyboard 0

    ################################################################################
    # Disable all sound. Otherwise logcat get polluted by audio_hw_generic: Hardware backing HAL too slow
    $ANDROID_HOME/platform-tools/adb $SELECT shell settings put system sound_effects_enabled 0

    ################################################################################
    # Disable animations for more speed and less flakiness on emulators.
    $ANDROID_HOME/platform-tools/adb $SELECT shell settings put global window_animation_scale 0
    $ANDROID_HOME/platform-tools/adb $SELECT shell settings put global transition_animation_scale 0
    $ANDROID_HOME/platform-tools/adb $SELECT shell settings put global animator_duration_scale 0
    
    ################################################################################
    # Removed what could be removed, everything less gives more performance for us.
    # Too slow for the gain at the end.
    # $ANDROID_HOME/platform-tools/adb shell 'pm list packages -f' | sed -e 's/.*=//' | sed -e "s#^#$ANDROID_HOME/platform-tools/adb uninstall #" | sort | bash
    
    ################################################################################
    # Disable what could be disabled, everything less gives more performance for us.
    # Automatically disable all is possible but results in defect emulator ... so you have to pic some of the annoying
    # $ANDROID_HOME/platform-tools/adb shell 'pm list packages -f' | sed -e 's/.*=//' | sed -e "s#\(.*\)#$ANDROID_HOME/platform-tools/adb shell 'su root pm disable \1'#" | sort | awk '{system($0)}'
    
    ################################################################################
    # Kill wait could be killed, everything less gives more performance for us.
    # Too slow for the gain at the end.
    # $ANDROID_HOME/platform-tools/adb shell ps | grep -v PID | awk '{print $2}' | xargs $ANDROID_HOME/platform-tools/adb shell kill 2> /dev/null
    
    ################################################################################
    # Try to dismiss random "not responding" system dialog.
    CURRENT_FOCUS=`$ANDROID_HOME/platform-tools/adb $SELECT shell dumpsys window windows | grep -E 'mCurrentFocus|mFocusedApp'`
    echo "$CURRENT_FOCUS"
    FOCUSED_APP_PACKAGE=`echo "$CURRENT_FOCUS" | grep "mCurrentFocus" | cut -d" " -f5 | cut -d"/" -f1`
    echo $FOCUSED_APP_PACKAGE
    if [[ $FOCUSED_APP_PACKAGE != *"launcher"* ]]; then
      echo "How can we kill or dismiss it?? See initial screenshot for more details."
      # $ANDROID_HOME/platform-tools/adb shell 'su root pm disable $FOCUSED_APP_PACKAGE
    
      FOCUSED_APP_PID=`$ANDROID_HOME/platform-tools/adb $SELECT shell ps | grep -v PID | grep $FOCUSED_APP_PACKAGE | awk '{print $2}'`
      $ANDROID_HOME/platform-tools/adb $SELECT shell su root kill $FOCUSED_APP_PID
    fi  
}

source azure/function-parallel-command.sh

if [[ -z "$@" ]]; then
    optimize
else
    runParallelForEachValue optimize $@
fi