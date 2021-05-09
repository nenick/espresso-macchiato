package de.nenick.android.emulator.task

import com.android.ddmlib.CollectingOutputReceiver
import com.android.ddmlib.IDevice
import de.nenick.android.emulator.setup.AndroidSetup
import de.nenick.android.emulator.tool.AdbShell
import de.nenick.android.emulator.tool.SimpleTimer
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class FixContentMediaAndroid : DefaultTask() {

    private val adbShell = AdbShell(project)

    @TaskAction
    fun fix() {
        adbShell.forEachConnectedDeviceParallel {
            printContentMedia(it)

            val setup = AndroidSetup.search(it.version)
            if (setup.shortCutContentMediaAndroid()) {
                createContentMediaAndroid(it, setup)
            }

            if (!hasContentMediaAndroid(it)) {
                println("Android data directory not found, try to create it explicitly")
                createContentMediaAndroid(it, setup)
            }

            printContentMedia(it)

            println("Android data directory, found")
        }
    }

    private fun printContentMedia(it: IDevice) {
        adbShell.execAdbShell(it, AdbShell.StdOutLogger, queryContentMedia)
    }

    private fun createContentMediaAndroid(device: IDevice, setup: AndroidSetup) {
        adbShell.execAdbShell(device, AdbShell.StdOutLogger, "$insertContentMedia${setup.externalDirectory()}/Android")
    }

    private fun hasContentMediaAndroid(device: IDevice): Boolean {
        val timer = SimpleTimer(timeoutSearchMilliseconds)
        while (true) {
            val receiver = CollectingOutputReceiver()
            adbShell.execAdbShell(device, receiver, queryContentMediaAndroid)

            if (receiver.output.contains(Regex(".*Android"))) {
                return true
            }

            if (timer.isTimeout()) {
                return false
            }

            println("Android data directory, wait ...")
            Thread.sleep(delayNextAttemptMilliseconds)
        }
    }

    companion object {
        private const val contentMedia = """--uri content://media/external/file"""
        private const val queryContentMedia = """content query $contentMedia --projection _data"""
        private const val queryContentMediaAndroid = """$queryContentMedia --where "_data LIKE '%Android'""""
        private const val insertContentMedia = """content insert $contentMedia --bind _data:s:"""
        private const val delayNextAttemptMilliseconds = 5 * 1000L
        private const val timeoutSearchMilliseconds = 30 * 1000L
    }
}