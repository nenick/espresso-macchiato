package com.example.esp_system_dialogs;

import de.nenick.espressomacchiato.tools.EspResourceTool;

/**
 * Actions and assertions for an Application Error dialog.
 *
 * @since Espresso Macchiato 0.4
 */
public class EspSystemAerrDialog extends EspSystemDialog {

    /**
     * Create new element instance.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.4
     */
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
