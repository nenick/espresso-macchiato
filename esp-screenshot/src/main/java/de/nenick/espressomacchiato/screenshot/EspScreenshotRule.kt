package de.nenick.espressomacchiato.screenshot

import de.nenick.espressomacchiato.screenshot.internal.AdditionalTestDataZipper
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.File

/**
 * Takes screenshot on test failure.
 *
 * Screenshots will be available also when using gradle commands. They are stored in `test_data`
 * and this directory will be automatically downloaded by android gradle tools before app becomes
 * uninstalled. See `build/outputs/connected_android_test_additional_output` for downloaded files.
 */
class EspScreenshotRule : TestWatcher() {

    companion object {
        init {
            // Prepare zip archive to store screenshots. Zip archive will be downloaded by android
            // gradle tools. The companion object init is only called once when class is first time
            // accessed and then never again. Good point to create the initial archive.
            AdditionalTestDataZipper
        }
    }

    override fun failed(e: Throwable, description: Description) {
        val testClassDir = File(AdditionalTestDataZipper.screenshotDirectory, description.className)
        val testMethodDir = File(testClassDir, description.methodName)

        val screenshotName = "${System.currentTimeMillis()} failure.png"
        val screenshotFile = File(testMethodDir, screenshotName)

        EspScreenshotTool.takeScreenshot(screenshotFile)
        AdditionalTestDataZipper.addToZipArchive(screenshotFile)
    }
}

