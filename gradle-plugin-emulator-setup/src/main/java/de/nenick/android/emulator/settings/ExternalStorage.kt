package de.nenick.android.emulator.settings

import com.android.sdklib.AndroidVersion
import de.nenick.android.emulator.tool.EmulatorDirectory

object ExternalStorage {

    fun avdArgumentSdCard(android: Int) = if (android < AndroidVersion.VersionCodes.M) {
        // Seems like emulator for early android versions need sdcard setting at creation time
        // instead of reading it from config.ini file.
        arrayOf("--sdcard", "512M")
    } else {
        // On android api 30 this sdcard argument would block downloading test_data because it start
        // searching on a path where test_data content never appears. But works properly with sdcard
        // settings in the configuration file.
        emptyArray()
    }

    fun configureSdCard(directory: EmulatorDirectory) {
        directory.config("hw.sdCard", "yes")
    }
}