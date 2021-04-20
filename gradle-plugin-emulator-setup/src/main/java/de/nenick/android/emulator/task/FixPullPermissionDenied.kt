package de.nenick.android.emulator.task

import com.android.ddmlib.AdbCommandRejectedException
import com.android.ddmlib.IDevice
import de.nenick.android.emulator.setup.AndroidSetup
import de.nenick.android.emulator.tool.AdbShell
import de.nenick.android.emulator.tool.SimpleTimer
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class FixPullPermissionDenied : DefaultTask(), AdbShell {

    @TaskAction
    fun fix() {
        forEachConnectedDeviceParallel {
            if (AndroidSetup.search(it.version).shouldRemountAsRoot()) {
                printAdbUserId(it)
                execAdbRoot(it, "remount")
                printAdbUserId(it)
                check(it.isRoot) { "Adb failed to get root rights." }
            }
        }
    }

    private fun printAdbUserId(device: IDevice) {
        repeatUntilSuccess {
            execAdbShell(device, AdbShell.StdOutLogger, "echo \"userId is \$USER_ID (0 = root rights)\"")
        }
    }

    private fun repeatUntilSuccess(block: () -> Unit) {
        val timer = SimpleTimer(timeoutRepeatUserIdRequestMilliseconds)
        while (true) {
            try {
                block()
                break
            } catch (e: AdbCommandRejectedException) {
                if (timer.isTimeout()) {
                    throw IllegalStateException("Couldn't get info about root status.", e)
                } else {
                    Thread.sleep(delayNextAttemptMilliseconds)
                }
            }
        }
    }

    companion object {
        private const val delayNextAttemptMilliseconds = 20L
        private const val timeoutRepeatUserIdRequestMilliseconds = 2 * 1000L
    }
}