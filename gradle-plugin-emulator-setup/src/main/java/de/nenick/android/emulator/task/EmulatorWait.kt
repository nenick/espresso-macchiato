package de.nenick.android.emulator.task

import com.android.build.gradle.BaseExtension
import com.android.ddmlib.AndroidDebugBridge
import com.android.ddmlib.IDevice
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class EmulatorWait: DefaultTask() {
    @TaskAction
    fun doIt() {
        forEachConnectedDevice {
            println(it)
        }
    }

    private fun forEachConnectedDevice(block: (IDevice) -> Unit) {
        val androidExtension = project.extensions.findByType(BaseExtension::class.java)!!

        ///-----
        // https://stackoverflow.com/questions/34186108/how-to-query-all-connected-devices-in-build-gradle

        AndroidDebugBridge.initIfNeeded(false)
        val bridge = AndroidDebugBridge.createBridge(androidExtension.adbExecutable.absolutePath, false)
        var timeOut = 30000L
        val sleepTime = 1000L
        while (!bridge.hasInitialDeviceList() && timeOut > 0) {
            Thread.sleep(sleepTime)
            timeOut -= sleepTime
        }
        if (timeOut <= 0 && !bridge.hasInitialDeviceList()) {
            throw RuntimeException("Timeout getting device list.")
        }
        val devices = bridge.devices
        if (devices.isEmpty()) {
            throw RuntimeException("No connected devices!")
        }
        devices.forEach {
            block(it)
        }
    }
}