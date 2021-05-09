package de.nenick.android.emulator.tool

import org.gradle.api.Project
import java.io.File

class EmulatorDirectory(private val project: Project, index: Int) {

    // TODO append -${index.toString().padStart(2, '0')} for parallel support after all tasks use the same avd naming.
    val name = "android-ci"
    private val configFilePath = "${System.getProperty("user.home")}/.android/avd/$name.avd/config.ini"

    fun config(setting: String, value: String) {
        replaceConfig("$setting=.*", "$setting=$value")
    }

    fun configNoToAll() {
        replaceConfig("=yes", "=no")
        check(!File(configFilePath).readText().contains("yes")) {
            "Still some settings are active. Issue with our regex?"
        }
    }

    private fun replaceConfig(match: String, replace: String) {
        val regex = "s/$match/$replace/g"
        LocalShell.exec(project, "sed", "-i", "", regex, configFilePath)
    }
}