package de.nenick.android.emulator.task

import com.android.build.gradle.BaseExtension
import de.nenick.android.emulator.setup.AndroidSetup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.process.ExecSpec
import java.io.File

open class CreateEmulator : DefaultTask() {

    @Internal
    @Option(option = "android", description = "Specify the android version for the emulator.")
    var android: String = "29"

    @Internal
    @Option(option = "count", description = "Amount of parallel emulators.")
    var count: String = "1"

    private val androidExtension = project.extensions.findByType(BaseExtension::class.java)!!

    @TaskAction
    fun create() {
        val setup = AndroidSetup.search(android.toInt())
        installSystemImage(setup)
        parallel(count.toInt()) { index ->
            val avdName = createAvdName(index)
            createAvd(avdName, setup)
            optimizeAvdSetting(avdName, setup)
        }
    }

    private fun createAvdName(index: Int) = if (count.toInt() == 1) {
        "android-ci"
    } else {
        "android-ci-$index"
    }

    private fun installSystemImage(setup: AndroidSetup) {
        exec {
            executable = File(androidExtension.sdkDirectory, "cmdline-tools/latest/bin/sdkmanager").absolutePath
            args = listOf("--install", setup.systemImage())
        }
    }

    private fun parallel(count: Int, block: (index: Int) -> Unit) {
        runBlocking {
            withContext(Dispatchers.Default) {
                for (i in 1..count) {
                    launch { block(i) }
                }
            }
        }
    }

    private fun createAvd(avdName: String, setup: AndroidSetup) {
        exec {
            executable = File(androidExtension.sdkDirectory, "cmdline-tools/latest/bin/avdmanager").absolutePath
            args = listOf(
                    "create", "avd",
                    "--name", avdName,
                    "--package", setup.systemImage(),
                    // Smaller screen result in more performance.
                    "--device", "2.7in QVGA",
                    "--force",
                    *setup.createAvdAdditionalArgs()
            )
        }
    }

    private fun optimizeAvdSetting(avdName: String, setup: AndroidSetup) {
        setup.avdSetting().forEach {
            updateConfigIni(avdName, it)
        }
    }

    private fun updateConfigIni(avdName: String, regex: String) {
        exec {
            executable = "sed"
            args = listOf("-i", "", regex, "${System.getProperty("user.home")}/.android/avd/$avdName.avd/config.ini")
        }
    }

    private fun exec(block: ExecSpec.() -> Unit) {
        project.exec {
            block(this)
            println("Run: ${commandLine.joinToString("\" \"", "\"", "\"")}")
        }
    }
}