package de.nenick.android.emulator.tool

import com.android.ddmlib.IDevice
import org.gradle.api.Project

class EmulatorInstance(private val project: Project, private val device: IDevice) {

    private val adbShell = AdbShell(project)

    fun isAndroidVersion(vararg versions: Int) = versions.contains(device.version.apiLevel)

    fun disableService(vararg services: String) {
        services.forEach { adbShell.execAdbShell(device, AdbShell.StdOutLogger, "su root pm disable $it") }
    }

    fun disablePackage(vararg packages: String) {
        packages.forEach { adbShell.execAdbShell(device, AdbShell.StdOutLogger, "su root pm disable $it") }
    }

    fun putSetting(namespace: String, setting: String, value: String) {
        adbShell.execAdbShell(device, AdbShell.StdOutLogger, "settings put $namespace $setting $value")
    }
}