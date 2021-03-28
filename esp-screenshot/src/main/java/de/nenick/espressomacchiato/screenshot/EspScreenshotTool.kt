package de.nenick.espressomacchiato.screenshot

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
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
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val device = UiDevice.getInstance(instrumentation)
        Assert.assertTrue(device.takeScreenshot(targetFile))
    }
}