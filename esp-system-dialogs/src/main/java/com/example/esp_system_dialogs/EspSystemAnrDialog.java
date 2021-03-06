package com.example.esp_system_dialogs;

import de.nenick.espressomacchiato.tools.EspResourceTool;

/**
 * Actions and assertions for an Application Not Responding dialog.
 *
 * @since Espresso Macchiato 0.4
 */
public class EspSystemAnrDialog extends EspSystemDialog {

    /**
     * Create new element instance.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.4
     */
    public static EspSystemAnrDialog build() {
        return new EspSystemAnrDialog();
    }

    @Override
    protected void dismissIfShownInternal() {
        // sometimes a system process isn't responding on emulator and this must be confirmed
        if (dialogIsShownWith(EspResourceTool.stringResourceByName("anr_process", ".*").replace("?", "\\?"))) {
            click(EspResourceTool.stringResourceByName("wait").toUpperCase());
        }
    }
}
