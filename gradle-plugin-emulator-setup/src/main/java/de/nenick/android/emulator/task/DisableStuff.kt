package de.nenick.android.emulator.task

import de.nenick.android.emulator.settings.InputMethod
import de.nenick.android.emulator.setup.AndroidSetup
import de.nenick.android.emulator.tool.AdbShell
import de.nenick.android.emulator.tool.EmulatorInstance
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

// Couldn't find a better name to describe what we disable.
// - Stuff just spamming the log
// - Stuff sometimes crashing and avoid view interactions
// - Stuff reducing the performance
open class DisableStuff : DefaultTask() {

    private val adbShell = AdbShell(project)

    @TaskAction
    fun disable() {
        adbShell.forEachConnectedDeviceParallel { device ->
            val setup = AndroidSetup.search(device.version)

            val instance = EmulatorInstance(project, device)

            InputMethod.disableSoftKeyboard(instance)
            InputMethod.disableInputRelatedStuff(instance)

            setup.disablePackages().forEach {
                adbShell.execAdbShell(device, AdbShell.StdOutLogger, "su root pm disable $it")
            }

            setup.disableServices().forEach {
                adbShell.execAdbShell(device, AdbShell.StdOutLogger, "su root pm disable $it")
            }

            setup.adjustSettings().forEach {
                adbShell.execAdbShell(device, AdbShell.StdOutLogger, "settings put $it")
            }
        }
    }
}