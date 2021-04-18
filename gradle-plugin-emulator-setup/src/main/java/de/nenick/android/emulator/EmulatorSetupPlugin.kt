package de.nenick.android.emulator

import de.nenick.android.emulator.task.EmulatorCreate
import org.gradle.api.Plugin
import org.gradle.api.Project

class EmulatorSetupPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register("createEmulator", EmulatorCreate::class.java) { group = "emulator" }
    }
}