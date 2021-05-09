package de.nenick.android.emulator.settings

import com.android.sdklib.AndroidVersion.VersionCodes.*
import de.nenick.android.emulator.tool.EmulatorDirectory

object EmulatorHardware {

    private const val armUnsupported = "Only arm architecture available. This variant isn't tested yet."

    fun systemImage(androidVersion: Int): String {
        val imageAbi = when {
            // Early android version don't have 64 bit variant.
            androidVersion <= KITKAT -> "x86"

            // Google does not provide any x86 variant for this version.
            androidVersion == KITKAT_WATCH -> throw UnsupportedOperationException(armUnsupported)

            // Does not have a 64 bit variant.
            androidVersion == O_MR1 -> "x86"

            // By default use 64 bit variant for a bit more performance.
            else -> "x86_64"
        }
        return "system-images;android-$androidVersion;google_apis;$imageAbi"
    }

    fun avdArgument(androidVersion: Int) = arrayOf(
        "--package", systemImage(androidVersion),

        // Small screen size and low dpi does increase the test run performance.
        "--device", "2.7in QVGA"
    )

    fun configure(directory: EmulatorDirectory) {
        // GPU should still be enabled for performance.
        directory.config("hw.gpu.enabled", "yes")

        // Don't be stingy with your resources.
        directory.config("hw.ramSize", "2048")
        directory.config("vm.heapSize", "256")

        // Virtual scene has ugly popup and possibility to reduce test performance.
        directory.config("hw.camera.back", "emulated")
    }
}