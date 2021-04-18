package de.nenick.android.emulator.tool

import com.android.build.gradle.BaseExtension
import com.android.ddmlib.AndroidDebugBridge
import com.android.ddmlib.IDevice
import com.android.ddmlib.IShellOutputReceiver
import com.android.ddmlib.MultiLineReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.gradle.api.Task
import java.util.concurrent.TimeUnit

interface AdbShell : Task {

    object StdOutLogger : MultiLineReceiver() {
        override fun isCancelled() = false
        override fun processNewLines(lines: Array<out String>) = lines.forEach { println(it) }
    }

    fun forEachConnectedDeviceParallel(block: (IDevice) -> Unit) {
        val devices = findConnectedDevices()
        runBlocking {
            withContext(Dispatchers.Default) {
                devices.forEach {
                    launch { block(it) }
                }
            }
        }
    }

    fun execAdbShell(it: IDevice, receiver: IShellOutputReceiver, command: String) {
        printCommandLineExecutable(it, command)
        it.executeShellCommand(command, receiver, timeoutExecuteShellCommandMinutes, TimeUnit.MINUTES)
    }

    fun printCommandLineExecutable(device: IDevice, command: String) {
        val commandlinePrepared = command
                .replace("'", "\\\"")
                .replace("\n", "; ")
                .replace(Regex("do *;"), "do")
        println("Run: \"${getAdbExecutablePath()}\" \"-s\" \"${device.serialNumber}\" \"shell\" '$commandlinePrepared'")
    }

    private fun findConnectedDevices(): Array<out IDevice> {
        val adbPath = getAdbExecutablePath()

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

    private fun getAdbExecutablePath(): String {
        val androidExtension = project.extensions.findByType(BaseExtension::class.java)!!
        return androidExtension.adbExecutable.absolutePath
    }

    companion object {
        private const val delayNextAttemptMilliseconds = 5 * 1000L
        private const val timeoutCreateAdbBridgeMinutes = 10L
        private const val timeoutFindDevicesMilliseconds = 60 * 1000L
        private const val timeoutExecuteShellCommandMinutes = 10L
    }
}