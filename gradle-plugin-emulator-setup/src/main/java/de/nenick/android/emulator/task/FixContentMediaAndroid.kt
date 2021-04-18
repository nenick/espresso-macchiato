package de.nenick.android.emulator.task

import com.android.ddmlib.CollectingOutputReceiver
import com.android.ddmlib.IDevice
import de.nenick.android.emulator.setup.AndroidSetup
import de.nenick.android.emulator.tool.AdbShell
import de.nenick.android.emulator.tool.SimpleTimer
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class FixContentMediaAndroid : DefaultTask(), AdbShell {

    @TaskAction
    fun fix() {
        forEachConnectedDeviceParallel {
            if (!hasContentMediaAndroid(it)) {
                println("Android data directory not found, try to create it explicitly")
                createContentMediaAndroid(it)
            }

            printContentMedia(it)

            println("Android data directory, found")
        }
    }

    private fun printContentMedia(it: IDevice) {
        execAdbShell(it, AdbShell.StdOutLogger, """content query --uri content://media/external/file --projection _data""")
    }

    private fun createContentMediaAndroid(device: IDevice) {
        val setup = AndroidSetup.search(device.version)
        val command = """content insert --uri content://media/external/file --bind _data:s:${setup.androidAppDataPath()}"""
        execAdbShell(device, AdbShell.StdOutLogger, command)
    }

    private fun hasContentMediaAndroid(device: IDevice): Boolean {
        val timer = SimpleTimer(timeoutSearchMilliseconds)
        while (true) {
            val receiver = CollectingOutputReceiver()
            // Bash escaped variant to run it directly on command line:
            // $ANDROID_HOME/platform-tools/adb $SELECT shell content query --uri content://media/external/file --projection _data --where "_data\ LIKE\ \'%Android\'"
            execAdbShell(device, receiver, """content query --uri content://media/external/file --projection _data --where "_data LIKE '%Android'"""")

            if (receiver.output.contains(Regex(".*Android"))) {
                return true
            }

            if (timer.isTimeout()) {
                return false
            }

            println("Android data directory, wait ...")
            Thread.sleep(delayNextAttemptMilliseconds)
        }
    }

    companion object {
        private const val delayNextAttemptMilliseconds = 5 * 1000L
        private const val timeoutSearchMilliseconds = 30 * 1000L
    }
}