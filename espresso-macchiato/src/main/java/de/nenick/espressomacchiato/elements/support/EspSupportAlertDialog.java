package de.nenick.espressomacchiato.elements.support;

import de.nenick.espressomacchiato.elements.EspDialog;

/**
 * Pre configured {@link EspDialog} for common {@link androidx.appcompat.app.AlertDialog}.
 *
 * @since Espresso Macchiato 0.4
 */
public class EspSupportAlertDialog extends EspDialog {

    /**
     * Create new element instance.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.4
     */
    public static EspSupportAlertDialog build() {
        return new EspSupportAlertDialog(EspDialog.spec()
                .withRoot(com.google.android.material.R.id.parentPanel)
                .withTitle(com.google.android.material.R.id.alertTitle)
                .withMessage(android.R.id.message)
                .withConfirmButton(android.R.id.button1)
                .withDenyButton(android.R.id.button2)
                .withCancelButton(android.R.id.button3));
    }

    /**
     * Create new instance with given specification.
     *
     * @param spec Dialog specification for common buttons and text views.
     *
     * @since Espresso Macchiato 0.4
     */
    public EspSupportAlertDialog(Spec spec) {
        super(spec);
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspSupportAlertDialog(EspSupportAlertDialog template) {
        super(template);
    }

    /**
     * @since Espresso Macchiato 0.4
     * @deprecated Currently not supported please use a different element creator.
     */
    @Deprecated // mark parent static method as not usable for this class
    public static Spec spec() {
        throw new UnsupportedOperationException();
    }
}
