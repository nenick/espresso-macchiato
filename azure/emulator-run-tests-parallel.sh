#!/usr/bin/env bash

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

# https://medium.com/mesmerhq/shard-your-android-espresso-tests-for-faster-execution-in-parallel-e66f1b5061ae

run() {
    env ANDROID_SERIAL=emulator-$1 ./gradlew \
        connectedDebugAndroidTest \
        -Pandroid.testInstrumentationRunnerArguments.numShards=$2 \
        -Pandroid.testInstrumentationRunnerArguments.shardIndex=$3 \
        -PtestReportsDir=build/testReports/shard$3 \
        -PtestResultsDir=build/testResults/shard$3 \
        | grep --line-buffered -v UP-TO-DATE | grep --line-buffered -v NO-SOURCE
}

source azure/function-parallel-command.sh

# We revert the shard order because the system tests goes as whole package to the second shard.
# And the second emulators starts a little bit later because the second gradle daemon have to
# be started first. So to balance the execution time we switch the target emulator.
POS=0
COMMANDS=()
for PORT in $@; do
    COMMANDS+=("run $PORT $# $POS")
    POS=$(($POS + 1))
done

runParallel "${COMMANDS[@]}"