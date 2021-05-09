package de.nenick.android.emulator.task

import com.android.build.gradle.BaseExtension
import de.nenick.android.emulator.settings.EmulatorHardware
import de.nenick.android.emulator.settings.ExternalStorage
import de.nenick.android.emulator.settings.InputMethod
import de.nenick.android.emulator.tool.EmulatorDirectory
import de.nenick.android.emulator.tool.LocalShell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

open class CreateEmulator : DefaultTask() {

    @Internal
    @Option(option = "android", description = "Android api level for the emulator.")
    var android: String = "30"

    @Internal
    @Option(option = "count", description = "Amount of parallel emulators.")
    var count: String = "1"

    private val androidExtension = project.extensions.findByType(BaseExtension::class.java)!!

    @TaskAction
    fun create() {
        installSystemImage()

        parallel { directory ->
            createAvd(directory.name)
            configureAvd(directory)
        }
    }

    private fun installSystemImage() {
        execTool("sdkmanager", "--install", EmulatorHardware.systemImage(android.toInt()))
    }

    private fun parallel(block: (directory: EmulatorDirectory) -> Unit) {
        runBlocking {
            withContext(Dispatchers.Default) {
                for (i in 1..count.toInt()) {
                    launch { block(EmulatorDirectory(project, i)) }
                }
            }
        }
    }

    private fun createAvd(avdName: String) {
        execTool("avdmanager", "create", "avd", "--name", avdName,

            // Some basic hardware configurations.
            *EmulatorHardware.avdArgument(android.toInt()),

            // Sdcard is most times necessary to have a valid external storage for stuff like screenshots.
            *ExternalStorage.avdArgumentSdCard(android.toInt()),

            "--force"
        )
    }

    private fun configureAvd(directory: EmulatorDirectory) {
        // First disable all stuff by default to eliminate possible performance reduction.
        directory.configNoToAll()

        // Some basic hardware configurations.
        EmulatorHardware.configure(directory)

        // Sdcard is most times necessary to have a valid external storage for stuff like screenshots.
        ExternalStorage.configureSdCard(directory)

        // This is a necessary preparation step to disable the soft keyboard.
        InputMethod.activateHardwareKeyboard(directory)
    }

    private fun execTool(tool: String, vararg arguments: String) {
        val toolLocation = File(androidExtension.sdkDirectory, "cmdline-tools/latest/bin/$tool")
        if (!toolLocation.exists()) {
            throw IllegalStateException("$toolLocation not found. Try install it through Android Studio " +
                "-> Tools -> SDK Manager -> SDK Tools -> Android SDK Command-Line Tools.")
        }
        LocalShell.exec(project, toolLocation.absolutePath, *arguments)
    }
}