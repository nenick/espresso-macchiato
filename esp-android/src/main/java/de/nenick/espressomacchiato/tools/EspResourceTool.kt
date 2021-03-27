package de.nenick.espressomacchiato.tools

import androidx.test.platform.app.InstrumentationRegistry

class EspResourceTool {
    companion object {
        fun androidId(idName: String) = InstrumentationRegistry.getInstrumentation().context.resources
                .getIdentifier(idName, "id", "android")
    }
}