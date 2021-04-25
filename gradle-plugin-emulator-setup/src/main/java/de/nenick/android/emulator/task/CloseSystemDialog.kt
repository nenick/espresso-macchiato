package de.nenick.android.emulator.task

import com.android.ddmlib.CollectingOutputReceiver
import com.android.ddmlib.IDevice
import de.nenick.android.emulator.tool.AdbShell
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class CloseSystemDialog : DefaultTask(), AdbShell {

    @TaskAction
    fun close() {
        forEachConnectedDeviceParallel {
            val windows = collectWindowInfo(it)

            if (isCrashDialogDisplayed(windows)) {
                // You can force it by just throwing a simple exception. Not forcible since android api 28.
                dismissSystemDialog(it)
            }

            if (isAnrDialogDisplayed(windows)) {
                // You can force ANR by adding following to your activity. Then start, click, press back,  wait ...
                //     override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
                //        while (true) {
                //        }
                //        return super.dispatchTouchEvent(ev)
                //    }
                dismissSystemDialog(it)
            }
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

    private fun isAnrDialogDisplayed(output: String) = output.contains("Application Not Responding")
    private fun isCrashDialogDisplayed(output: String) = output.contains("Application Error")
}