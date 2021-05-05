package de.nenick.android.emulator.task

import com.android.ddmlib.CollectingOutputReceiver
import com.android.ddmlib.IDevice
import com.android.sdklib.AndroidVersion
import de.nenick.android.emulator.tool.AdbShell
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class CloseSystemDialog : DefaultTask(), AdbShell {

    @TaskAction
    fun close() {
        forEachConnectedDeviceParallelRepeated {
            val windows = collectWindowInfo(it)
            var repeatAfterFoundSystemDialog = false

            if (it.version.apiLevel == AndroidVersion.VersionCodes.JELLY_BEAN) {
                // Window dump on this android version does not contain the reason message.
                // But we can check that the launcher is focused.
                if (!isLauncherFocused(windows)) {
                    dismissSystemDialog(it)
                    repeatAfterFoundSystemDialog = true
                }
            } else {
                if(contains(windows, crash) || contains(windows, anr)) {
                    dismissSystemDialog(it)
                    repeatAfterFoundSystemDialog = true
                }
            }

            repeatAfterFoundSystemDialog
        }
    }

    private fun isLauncherFocused(windows: String) =
        windows.contains(Regex(".*mCurrentFocus=Window.*com.android.launcher.*")).also {
            if(!it) {
                println(windows)
            }
        }

    private fun dismissSystemDialog(device: IDevice) {
        // Sometimes dialog has one and sometimes has two buttons. But in every case we want to click the "last"
        // option to just accept the situation.
        // https://stackoverflow.com/questions/39457305/android-testing-waited-for-the-root-of-the-view-hierarchy-to-have-window-focus/54203607#54203607
        execAdbShell(device, AdbShell.StdOutLogger, "input keyevent 20") // down (get focus or select bottom button)
        execAdbShell(device, AdbShell.StdOutLogger, "input keyevent 20") // down (select bottom button)
        execAdbShell(device, AdbShell.StdOutLogger, "input keyevent 66") // enter (click wait/close/ok button)
    }

    private fun collectWindowInfo(device: IDevice): String {
        val receiver = CollectingOutputReceiver()
        execAdbShell(device, receiver, "dumpsys window windows")
        return receiver.output
    }

    private fun contains(text: String, info: String): Boolean {
        return text.contains(info).also {
            if (it) {
                val start = text.indexOf(info)
                val end = text.indexOf("\n", start)
                println(text.subSequence(start, end))
            }
        }
    }

    companion object {
        private const val anr = "Application Not Responding"
        private const val crash = "Application Error"
    }
}