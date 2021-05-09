package de.nenick.android.emulator.task

import com.android.sdklib.AndroidVersion
import de.nenick.android.emulator.tool.AdbShell
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

open class TakeScreenshot : DefaultTask() {

    @Input
    @Option(option = "directory", description = "Location to save the screenshot.")
    lateinit var directory: String

    @Input
    @Option(option = "name", description = "Base name to save screenshot.")
    lateinit var fileName: String

    private val adbShell = AdbShell(project)

    @TaskAction
    fun take() {
        adbShell.forEachConnectedDeviceParallel {
            val screenshotFile = File("$directory/${it.serialNumber}", "$fileName-${it.serialNumber}.png")
            screenshotFile.parentFile.mkdirs()

            val screenshotCommand = if (it.version.apiLevel < AndroidVersion.VersionCodes.LOLLIPOP) {
                // The exec-out does not exist on pre android api 21.
                // https://stackoverflow.com/questions/13578416/read-binary-stdout-data-from-adb-shell
                "${AdbShell.AdbCommand.executable(project)} " +
                    "${AdbShell.AdbCommand.selectDevice(it).joinToString(" ")} " +
                    "shell screencap -p " +
                    "| perl -pe 's/\\x0D\\x0A/\\x0A/g' " +
                    "> ${screenshotFile.path}"
            } else {
                "${AdbShell.AdbCommand.executable(project)} " +
                    "${AdbShell.AdbCommand.selectDevice(it).joinToString(" ")} " +
                    "exec-out screencap -p " +
                    "> ${screenshotFile.path}"
            }

            project.exec {
                executable = "bash"
                args = listOf("-")
                workingDir = project.rootDir
                standardInput = screenshotCommand.byteInputStream()
                println("Exec: $screenshotCommand")
            }
        }
    }
}