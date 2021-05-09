package de.nenick.android.emulator.settings

import com.android.sdklib.AndroidVersion.VersionCodes.KITKAT
import de.nenick.android.emulator.tool.EmulatorDirectory
import de.nenick.android.emulator.tool.EmulatorInstance

object InputMethod {

    fun activateHardwareKeyboard(directory: EmulatorDirectory) {
        // Disabling the soft keyboard is only possible when the emulator can get input from physical keyboard.
        directory.config("hw.keyboard", "yes")
    }

    fun disableSoftKeyboard(instance: EmulatorInstance) {
        instance.putSetting("secure", "show_ime_with_hard_keyboard", "0")
    }

    fun disableInputRelatedStuff(instance: EmulatorInstance) {
        // There is still some input related stuff running and possibly taking performance from our test runs.
        // But some android versions would produce continuously appearing system crash dialogs after disabling them.
        if (instance.version == KITKAT) return

        instance.disableApp(
            // Crashed on android 27
            "com.google.android.inputmethod.latin"
        )

        instance.disableService(
            "com.google.android.googlequicksearchbox/com.google.android.voicesearch.ime.VoiceInputMethodService"
        )
    }
}