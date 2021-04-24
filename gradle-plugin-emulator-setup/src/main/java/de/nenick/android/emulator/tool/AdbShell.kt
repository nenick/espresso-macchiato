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
import org.gradle.api.Project
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

    fun execAdbShell(device: IDevice, receiver: IShellOutputReceiver, script: String) {
        printAdbShellCommand(device, script)
        device.executeShellCommand(script, receiver, timeoutExecuteShellCommandMinutes, TimeUnit.MINUTES)
    }

    fun execAdbRoot(device: IDevice, command: String) {
        // Attempts with prepared AdbHelper didn't work properly. No clue how to let them behave like commandline
        // "adb root .." calls. The commandline root request works mostly immediately and the AdbHelper have to
        // wait for a certain emulator state until root request does works properly.
        // - AdbHelper.root() and
        // - AdbHelper.formAdbRequest("root:remount")
        execAdbCommandline(device, "root", command)
    }

    object AdbCommand {
        fun executable(project: Project): String {
            val androidExtension = project.extensions.findByType(BaseExtension::class.java)!!
            return androidExtension.adbExecutable.absolutePath
        }

        fun selectDevice(device: IDevice) = arrayOf("-s", device.serialNumber)
    }

    fun execAdbCommandline(device: IDevice, vararg arguments: String) {
        project.exec {
            executable = AdbCommand.executable(project)
            args = arrayOf(
                AdbCommand.selectDevice(device),
                arguments
            ).flatten()
            println("Exec: ${commandLine.joinToString("\" \"", "\"", "\"")}")
        }
    }

    private fun printAdbShellCommand(device: IDevice, deviceScript: String) {
        val deviceScriptPrintable = deviceScript
            // Ensure correct escaping when printing special characters to device
            .replace("'", "\\\"")
            // Form multiline script to single line, that's much easier to copy and paste for execution.
            .replace("\n", "; ")
            // Spacial case for loops, where ";" after "do" is wrong syntax.
            .replace(Regex("do *;"), "do")

        val adbShellCommand = listOf(
            AdbCommand.executable(project),
            *AdbCommand.selectDevice(device),
            "shell"
        )
        println("Exec: ${adbShellCommand.joinToString("\" \"", "\"", "\"")} '$deviceScriptPrintable'")
    }

    private fun findConnectedDevices(): Array<out IDevice> {
        val adbPath = AdbCommand.executable(project)

        // Sample to access devices is taken from:
        // https://stackoverflow.com/questions/34186108/how-to-query-all-connected-devices-in-build-gradle
        @Suppress("DEPRECATION" /* Only deprecated for non testing usage. */)
        AndroidDebugBridge.initIfNeeded(false)

        val bridge = AndroidDebugBridge.createBridge(adbPath, false, timeoutCreateAdbBridgeMinutes, TimeUnit.MINUTES)
        val timer = SimpleTimer(timeoutFindDevicesMilliseconds)

        while (!bridge.hasInitialDeviceList() && !timer.isTimeout()) {
            println("No initial devices list, yet.")
            Thread.sleep(delayNextAttemptMilliseconds)
        }
        check(bridge.hasInitialDeviceList()) { "Timeout getting device list." }

        while (bridge.devices.isEmpty() && !timer.isTimeout()) {
            println("No connected devices, yet.")
            // Pre android api 20 emulators need a moment before they are connected with adb.
            Thread.sleep(delayNextAttemptMilliseconds)
        }

        val devices = bridge.devices
        check(devices.isNotEmpty()) { "No connected devices!" }

        return devices
    }

    companion object {
        private const val delayNextAttemptMilliseconds = 5 * 1000L
        private const val timeoutCreateAdbBridgeMinutes = 10L
        private const val timeoutFindDevicesMilliseconds = 60 * 1000L
        private const val timeoutExecuteShellCommandMinutes = 10L
    }
}