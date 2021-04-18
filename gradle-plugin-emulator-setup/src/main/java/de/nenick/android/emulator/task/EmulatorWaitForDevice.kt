package de.nenick.android.emulator.task

import com.android.ddmlib.IDevice
import de.nenick.android.emulator.tool.AdbShell
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class EmulatorWaitForDevice : DefaultTask(), AdbShell {

    private val waitForBootCompleted = """
        while [[ -z $(getprop sys.boot_completed) ]]; do 
            echo "emulator is booting ..."
            sleep 5
        done
        input keyevent 82
    """.trimIndent()

    @TaskAction
    fun waitForDevice() {
        forEachConnectedDeviceParallel {
            waitForDevice(it)
            waitForBootCompleted(it)
            println("$it ready")
        }
    }

    private fun waitForDevice(it: IDevice) {
        while (it.state != IDevice.DeviceState.ONLINE) {
            println("${it.serialNumber} is launching ...")
            Thread.sleep(delayNextAttemptMilliseconds)
        }
    }

    private fun waitForBootCompleted(it: IDevice) {
        execAdbShell(it, AdbShell.StdOutLogger, waitForBootCompleted.replace("emulator", it.serialNumber))
    }

    companion object {
        private const val delayNextAttemptMilliseconds = 5 * 1000L
    }
}