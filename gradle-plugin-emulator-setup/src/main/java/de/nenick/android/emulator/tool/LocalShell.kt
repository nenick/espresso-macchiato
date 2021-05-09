package de.nenick.android.emulator.tool

import org.gradle.api.Project
import org.gradle.process.ExecSpec

object LocalShell {

    fun exec(project: Project, command: String, vararg arguments: String) {
        project.exec {
            executable = command
            args = arguments.toList()
            printCommand(this)
        }
    }

    private fun printCommand(spec: ExecSpec) {
        val preparedForCopyAndPaste = spec.commandLine.joinToString("\" \"", "\"", "\"")
        println("Exec: $preparedForCopyAndPaste")
    }
}