package de.nenick.android.emulator

import de.nenick.android.emulator.task.CloseSystemDialog
import de.nenick.android.emulator.task.CreateEmulator
import de.nenick.android.emulator.task.FixContentMediaAndroid
import de.nenick.android.emulator.task.FixPullPermissionDenied
import de.nenick.android.emulator.task.TakeScreenshot
import de.nenick.android.emulator.task.WaitForDevice
import org.gradle.api.Plugin
import org.gradle.api.Project

class EmulatorSetupPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.apply {
            register("createEmulator", CreateEmulator::class.java) { group = "emulator" }
            register("waitForEmulator", WaitForDevice::class.java) { group = "emulator" }
            register("fixContentMediaAndroid", FixContentMediaAndroid::class.java) { group = "emulator" }
            register("fixPullPermissionDenied", FixPullPermissionDenied::class.java) { group = "emulator" }
            register("takeScreenshot", TakeScreenshot::class.java) { group = "emulator" }
            register("closeSystemDialog", CloseSystemDialog::class.java) { group = "emulator" }
        }
    }
}