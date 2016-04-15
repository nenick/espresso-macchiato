package de.nenick.espressomacchiato.tools;

import android.support.test.InstrumentationRegistry;

public class EspResourceTool {

    public static String stringResourceByName(String name, String ... formatArgs) {
        // for all available strings see Android/sdk/platforms/android-23/data/res/values/strings.xml
        int resId = InstrumentationRegistry.getContext().getResources().getIdentifier(name, "string", "android");
        return InstrumentationRegistry.getContext().getString(resId, formatArgs);
    }
}
