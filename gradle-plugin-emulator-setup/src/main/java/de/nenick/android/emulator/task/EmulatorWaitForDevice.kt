package de.nenick.android.emulator.task

import com.android.build.gradle.BaseExtension
import com.android.ddmlib.AndroidDebugBridge
import com.android.ddmlib.IDevice
import com.android.ddmlib.MultiLineReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.util.concurrent.TimeUnit

open class EmulatorWaitForDevice : DefaultTask() {

    private val waitForBootCompleted = """
        while [[ -z $(getprop sys.boot_completed) ]]; do 
            echo "emulator is booting ..."
            sleep 5
        done
        input keyevent 82
    """.trimIndent()

    private val stdOutLogger = object : MultiLineReceiver() {
        override fun isCancelled() = false
        override fun processNewLines(lines: Array<out String>) = println(lines.joinToString())
    }

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
        execAdbShell(it, waitForBootCompleted.replace("emulator", it.serialNumber))
    }

    private fun execAdbShell(it: IDevice, command: String) {
        it.executeShellCommand(command, stdOutLogger, timeoutExecuteShellCommandMinutes, TimeUnit.MINUTES)
    }

    private fun forEachConnectedDeviceParallel(block: (IDevice) -> Unit) {
        val devices = findConnectedDevices()
        runBlocking {
            withContext(Dispatchers.Default) {
                devices.forEach {
                    launch { block(it) }
                }
            }
        }
    }

    private fun findConnectedDevices(): Array<out IDevice> {
        val androidExtension = project.extensions.findByType(BaseExtension::class.java)!!
        val adbPath = androidExtension.adbExecutable.absolutePath

        // https://stackoverflow.com/questions/34186108/how-to-query-all-connected-devices-in-build-gradle
        @Suppress("DEPRECATION" /* Only deprecated for non testing usage. */)
        AndroidDebugBridge.initIfNeeded(false)

        val bridge = AndroidDebugBridge.createBridge(adbPath, false, timeoutCreateAdbBridgeMinutes, TimeUnit.MINUTES)
        var timeOut = timeoutFindDevicesMilliseconds

        while (!bridge.hasInitialDeviceList() && timeOut > 0) {
            Thread.sleep(delayNextAttemptMilliseconds)
            timeOut -= delayNextAttemptMilliseconds
        }

        while (bridge.devices.isEmpty() && timeOut > 0) {
            println("No connected devices, yet.")
            // Pre android api 20 emulators need a moment before they are connected with adb.
            Thread.sleep(delayNextAttemptMilliseconds)
            timeOut -= delayNextAttemptMilliseconds
        }

        if (timeOut <= 0 && !bridge.hasInitialDeviceList()) {
            throw RuntimeException("Timeout getting device list.")
        }

        val devices = bridge.devices
        if (devices.isEmpty()) {
            throw RuntimeException("No connected devices!")
        }
        return devices
    }

    companion object {
        private const val delayNextAttemptMilliseconds = 5 * 1000L
        private const val timeoutCreateAdbBridgeMinutes = 10L
        private const val timeoutFindDevicesMilliseconds = 60 * 1000L
        private const val timeoutExecuteShellCommandMinutes = 10L
    }
}