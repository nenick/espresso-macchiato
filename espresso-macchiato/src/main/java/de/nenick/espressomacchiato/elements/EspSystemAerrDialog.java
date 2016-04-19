package de.nenick.espressomacchiato.elements;

import de.nenick.espressomacchiato.tools.EspResourceTool;

public class EspSystemAerrDialog extends EspSystemDialog {

    public static EspSystemAerrDialog build() {
        return new EspSystemAerrDialog();
    }


    @Override
    protected void dismissIfShownInternal() {
        // sometimes a system process does crash on emulator and this must be confirmed
        if (dialogIsShownWith(EspResourceTool.stringResourceByName("aerr_application", ".*"))) {
            click(EspResourceTool.stringResourceByName("ok"));
        }
    }
}
