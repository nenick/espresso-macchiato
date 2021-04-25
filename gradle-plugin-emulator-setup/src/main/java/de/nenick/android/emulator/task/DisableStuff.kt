package de.nenick.android.emulator.task

import de.nenick.android.emulator.setup.AndroidSetup
import de.nenick.android.emulator.tool.AdbShell
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

// Couldn't find a better name to describe what we disable.
// - Stuff just spamming the log
// - Stuff sometimes crashing and avoid view interactions
// - Stuff reducing the performance
open class DisableStuff : DefaultTask(), AdbShell {

    @TaskAction
    fun disable() {
        forEachConnectedDeviceParallel { device ->
            val setup = AndroidSetup.search(device.version)

            setup.disablePackages().forEach {
                execAdbShell(device, AdbShell.StdOutLogger, "su root pm disable $it")
            }
        }
    }
}