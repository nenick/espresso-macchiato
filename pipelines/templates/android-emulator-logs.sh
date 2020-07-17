#!/usr/bin/env bash

[ -z "${1}" ] && echo "Missing argument target file name (e.g. emulator-logs.txt)" && exit 1
TARGET_FILE=$1

[ `ps aux | grep  qemu -c` -ne 2 ] && echo "No emulator process found" && exit 1

################################################################################
# Collect logs from emulator and filter anying stuff
$ANDROID_HOME/platform-tools/adb logcat -d | grep -v GpsLocationProvider | grep -v MediaProvider | grep -v MetadataRetrieverClient | grep -v BackupManagerService | grep -v ARMAssembler | grep -v Choreographer | grep -v IInputConnectionWrapper | grep -v "I/art" | grep -v ConnectivityService | grep -v "D/PhoneInterfaceManager" | grep -v "I/Email" | grep -v "I/Exchange" | grep -v "E/libEGL" | grep -v "D/gralloc" | grep -v "E/installd" | grep -v "D/ConnectivityManager" | grep -v "D/WIFI" | grep -v "V/Mms" > $TARGET_FILE