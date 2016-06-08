package de.nenick.espressomacchiato.elements;

import de.nenick.espressomacchiato.tools.EspResourceTool;

/**
 * Pre configured {@link EspDialog} for common {@link android.app.AlertDialog}.
 *
 * @since Espresso Macchiato 0.2
 */
public class EspAlertDialog extends EspDialog {

    /**
     * Create new element instance.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.2
     */
    public static EspAlertDialog build() {
        return new EspAlertDialog(EspDialog.spec()
                .withRoot(EspResourceTool.idByName("parentPanel"))
                .withTitle(EspResourceTool.idByName("alertTitle"))
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
     * @since Espresso Macchiato 0.2
     */
    public EspAlertDialog(Spec spec) {
        super(spec);
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspAlertDialog(EspAlertDialog template) {
        super(template);
    }

    /**
     * @since Espresso Macchiato 0.2
     * @deprecated Currently not supported please use a different element creator.
     */
    @Deprecated
    public static Spec spec() {
        throw new UnsupportedOperationException();
    }
}
