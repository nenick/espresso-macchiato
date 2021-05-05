package de.nenick.android.emulator.task

import com.android.ddmlib.CollectingOutputReceiver
import com.android.ddmlib.IDevice
import de.nenick.android.emulator.tool.AdbShell
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class WaitForDevice : DefaultTask(), AdbShell {

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
            waitForLauncher(it)
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

    private fun waitForLauncher(it: IDevice) {
        while(true) {
            println("${it.serialNumber} launcher is showing up ...")
            val windows = collectWindowInfo(it)
            if(windows.contains("launcher")) {
                break
            }
            Thread.sleep(delayNextAttemptMilliseconds)
        }
    }

    private fun collectWindowInfo(device: IDevice): String {
        val receiver = CollectingOutputReceiver()
        execAdbShell(device, receiver, "dumpsys window windows")
        return receiver.output
    }

    companion object {
        private const val delayNextAttemptMilliseconds = 5 * 1000L
    }
}