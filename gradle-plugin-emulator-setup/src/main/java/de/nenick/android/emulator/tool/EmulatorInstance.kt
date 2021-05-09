package de.nenick.android.emulator.tool

import com.android.ddmlib.IDevice
import org.gradle.api.Project

class EmulatorInstance(project: Project, private val device: IDevice) {

    private val adbShell = AdbShell(project)

    val version = device.version.apiLevel

    // $ANDROID_HOME/platform-tools/adb shell dumpsys activity services
    fun disableService(vararg services: String) {
        services.forEach { adbShell.execAdbShell(device, AdbShell.StdOutLogger, "su root pm disable $it") }
    }

    // $ANDROID_HOME/platform-tools/adb shell su root pm list packages
    fun disableApp(vararg packages: String) {
        packages.forEach { adbShell.execAdbShell(device, AdbShell.StdOutLogger, "su root pm disable $it") }
    }

    fun putSetting(namespace: String, setting: String, value: String) {
        adbShell.execAdbShell(device, AdbShell.StdOutLogger, "settings put $namespace $setting $value")
    }
}