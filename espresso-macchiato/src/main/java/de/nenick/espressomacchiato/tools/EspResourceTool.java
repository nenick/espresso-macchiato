package de.nenick.espressomacchiato.tools;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;

public class EspResourceTool {

    public static String stringResourceByName(String name, String ... formatArgs) {
        // for all available strings see Android/sdk/platforms/android-23/data/res/values/strings.xml
        int resId = InstrumentationRegistry.getContext().getResources().getIdentifier(name, "string", "android");
        return InstrumentationRegistry.getContext().getString(resId, formatArgs);
    }

    public static int idByName(String name) {
        return InstrumentationRegistry.getContext().getResources().getIdentifier(name, "id", "android");

    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
