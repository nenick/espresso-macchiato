package de.nenick.espressomacchiato.elements;

import de.nenick.espressomacchiato.tools.EspResourceTool;

/**
 * Pre configured {@link EspDialog} for general {@link android.support.v7.app.AlertDialog}.
 */
public class EspAlertDialog extends EspDialog {

    @Deprecated // mark parent static method as not usable for this class
    public static Spec spec() {
        throw new UnsupportedOperationException();
    }

    public static EspAlertDialog build() {
        return new EspAlertDialog(EspDialog.spec()
                .withRoot(EspResourceTool.idByName("parentPanel"))
                .withTitle(EspResourceTool.idByName("alertTitle"))
                .withMessage(android.R.id.message)
                .withConfirmButton(android.R.id.button1)
                .withDenyButton(android.R.id.button2)
                .withCancelButton(android.R.id.button3));
    }

    public EspAlertDialog(Spec spec) {
        super(spec);
    }

    public EspAlertDialog(EspAlertDialog template) {
        super(template);
    }
}
