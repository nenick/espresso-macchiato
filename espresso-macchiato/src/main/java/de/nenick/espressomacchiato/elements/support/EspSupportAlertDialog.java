package de.nenick.espressomacchiato.elements.support;

import de.nenick.espressomacchiato.elements.EspDialog;

/**
 * Pre configured {@link EspDialog} for general {@link android.support.v7.app.AlertDialog}.
 */
public class EspSupportAlertDialog extends EspDialog {

    @Deprecated // mark parent static method as not usable for this class
    public static Spec spec() {
        throw new UnsupportedOperationException();
    }

    public static EspSupportAlertDialog build() {
        return new EspSupportAlertDialog(EspDialog.spec()
                .withRoot(android.support.design.R.id.parentPanel)
                .withTitle(android.support.design.R.id.alertTitle)
                .withMessage(android.R.id.message)
                .withConfirmButton(android.R.id.button1)
                .withDenyButton(android.R.id.button2)
                .withCancelButton(android.R.id.button3));
    }

    public EspSupportAlertDialog(Spec spec) {
        super(spec);
    }

    public EspSupportAlertDialog(EspSupportAlertDialog template) {
        super(template);
    }
}
