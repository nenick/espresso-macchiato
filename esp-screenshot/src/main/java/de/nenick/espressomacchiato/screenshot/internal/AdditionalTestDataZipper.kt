package de.nenick.espressomacchiato.screenshot.internal

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

internal object AdditionalTestDataZipper {

    private const val screenshotDirName = "screenshots"
    private val context = InstrumentationRegistry.getInstrumentation().context
    private val externalFilesDir = context.getExternalFilesDir(null)!!

    val screenshotDirectory = File(externalFilesDir, screenshotDirName).also {
        it.deleteRecursively()
        Assert.assertFalse(it.exists())
        it.mkdirs()
        Assert.assertTrue(it.exists())
    }

    // SimpleTestRunnable.queryAdditionalTestOutputLocation
    // https://android.googlesource.com/platform/tools/base/+/studio-master-dev/build-system/gradle-core/src/main/java/com/android/build/gradle/internal/testing/SimpleTestRunnable.java
    private val additionalTestDataDirectory = File(externalFilesDir, "test_data").also {
        it.mkdirs()
        Assert.assertTrue(it.exists())
    }

    private val zipFile = File(additionalTestDataDirectory, "$screenshotDirName.zip").also {
        // Remove previous zip file or new screenshots will be added to existing archive.
        it.delete()
        Assert.assertFalse(it.exists())
    }

    init {
        // Initial empty zip archive to have always an artifact to work with.
        ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use {
            it.putNextEntry(ZipEntry(screenshotDirName + File.separator))
            it.closeEntry()
        }
    }

    fun addToZipArchive(file: File) {
        val zipFileTemp = File(zipFile.parent, "$screenshotDirName-temp.zip")
        val zipFileTempStream = ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFileTemp)))

        addNewFile(file, zipFileTempStream)
        addExistingFiles(zipFileTempStream)

        // Hint: zip stream as to be closed, or it will be an invalid zip archive.
        zipFileTempStream.close()
        zipFileTemp.renameTo(zipFile)
    }

    private fun addExistingFiles(zipFileTempStream: ZipOutputStream) {
        if (zipFile.exists()) {
            val zip = ZipFile(zipFile)
            val zipEntries = zip.entries()

            while (zipEntries.hasMoreElements()) {
                val zipEntry: ZipEntry = zipEntries.nextElement()
                zipFileTempStream.putNextEntry(zipEntry)
                zip.getInputStream(zipEntry).copyTo(zipFileTempStream, 1024)
            }
        }
    }

    private fun addNewFile(file: File, zipFileTempStream: ZipOutputStream) {
        val relativeFilePath = file.relativeTo(screenshotDirectory.parentFile!!).path
        val entry = ZipEntry(relativeFilePath)

        zipFileTempStream.putNextEntry(entry)
        val inStream = BufferedInputStream(FileInputStream(file))
        inStream.copyTo(zipFileTempStream, 1024)
    }
}