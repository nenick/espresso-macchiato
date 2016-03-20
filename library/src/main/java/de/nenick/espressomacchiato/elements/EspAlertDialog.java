package de.nenick.espressomacchiato.elements;

public class EspAlertDialog extends EspDialog {

    /** hide {@link EspDialog#spec()} */
    @Deprecated
    public static Spec spec() {
        throw new UnsupportedOperationException();
    }

    public static EspAlertDialog build() {
        return new EspAlertDialog(EspDialog.spec()
                .withRoot(android.support.design.R.id.parentPanel)
                .withTitle(android.support.design.R.id.alertTitle)
                .withMessage(android.R.id.message)
                .withConfirmButton(android.R.id.button1)
                .withDenyButton(android.R.id.button2)
                .withCancelButton(android.R.id.button3));
    }

    public EspAlertDialog(Spec spec) {
        super(spec);
    }
}
