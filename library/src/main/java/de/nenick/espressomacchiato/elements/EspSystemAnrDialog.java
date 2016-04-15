package de.nenick.espressomacchiato.elements;

import de.nenick.espressomacchiato.tools.EspResourceTool;

public class EspSystemAnrDialog extends EspSystemDialog {

    public static EspSystemAnrDialog build() {
        return new EspSystemAnrDialog();
    }

    @Override
    protected void dismissIfShownInternal() {
        // sometimes a system process isn't responding on emulator and this must be confirmed
        if (dialogIsShownWith(EspResourceTool.stringResourceByName("anr_process", ".*").replace("?", "\\?"))) {
            click(EspResourceTool.stringResourceByName("wait"));
        }
    }
}
