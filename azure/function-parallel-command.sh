#!/usr/bin/env bash

# Task should fail if any bash command fail. Through bash pipelines the previous command result is by default ignored.
set -o pipefail

# param list of commands e.g. "ls /dev/null" "ls notExists" "ls /dev/null"
runParallel() {
  PROCESS_IDS=

  # Execute each action in background.
  for ACTION in "$@"; do
      $ACTION &

      # Remember the process to wait for it.
      PROCESS_IDS+="$! "
  done

  # Wait and collect amount of failed processes.
  FAIL_COUNTER=0
  for PID in $PROCESS_IDS; do
    wait $PID
    if [[ $? -ne 0 ]]; then
        FAIL_COUNTER=$(($FAIL_COUNTER+1))
    fi
  done

  # Print final info how many processes failed
  if [[ $FAIL_COUNTER -ne 0 ]]; then
      echo "finished with $FAIL_COUNTER of $# parallel processes failed" >&2
  else
      echo "all $# parallel processes where successful"
  fi

  return $FAIL_COUNTER
}

runParallelForEachValue() {
    echo "DEBUG: runParallelForEachValue args: $@"

    COMMAND=$1
    echo "DEBUG: runParallelForEachValue command: $COMMAND"

    shift 1
    echo "DEBUG: runParallelForEachValue values: $@"

    COMMANDS=()
    for PORT in $@; do
        COMMANDS+=("$COMMAND $PORT")
    done

    echo "DEBUG: runParallelForEachValue exec: $(printf '\n    %s' "${COMMANDS[@]}")"
    runParallel "${COMMANDS[@]}"

    return $?
}