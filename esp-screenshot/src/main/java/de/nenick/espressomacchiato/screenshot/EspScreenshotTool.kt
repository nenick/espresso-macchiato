package de.nenick.espressomacchiato.screenshot

import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import de.nenick.espressomacchiato.screenshot.internal.EspScreenshotToolPreJellyBeanMr2
import org.junit.Assert
import java.io.File

object EspScreenshotTool {

    /**
     * Takes screenshot and store it to the given path and file name.
     *
     * Usually you should use [android.content.Context.getExternalFilesDir] as path.
     */
    fun takeScreenshot(targetFile: File) {
        targetFile.parentFile!!.mkdirs()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val instrumentation = InstrumentationRegistry.getInstrumentation()
            val device = UiDevice.getInstance(instrumentation)
            Assert.assertTrue(device.takeScreenshot(targetFile))
        } else {
            EspScreenshotToolPreJellyBeanMr2().takeScreenShot(targetFile)
        }
    }
}