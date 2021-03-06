package de.nenick.espressomacchiato.elements;

import androidx.test.espresso.Espresso;

/**
 * Base for action and assertion for Dialogs.
 *
 * Recommend is to use more specific subclasses like {@link EspAlertDialog}.
 *
 * @since Espresso Macchiato 0.2
 */
public class EspDialog {

    protected Spec spec;

    /**
     * Start creating new element instance with given specification.
     *
     * @return New dialog specification builder.
     *
     * @since Espresso Macchiato 0.2
     */
    public static Spec spec() {
        return new Spec();
    }

    /**
     * Dialog specification builder.
     *
     * @since Espresso Macchiato 0.2
     */
    public static class Spec {

        private int rootResource;
        private int titleResource;
        private int messageResource;
        private int confirmButtonResource;
        private int denyButtonResource;
        private int cancelButtonResource;

        /**
         * Create new element instance with given specification.
         *
         * @return New element instance for actions and assertions.
         *
         * @since Espresso Macchiato 0.2
         */
        public EspDialog build() {
            return new EspDialog(this);
        }

        /**
         * Specify dialog base layout. (optional)
         *
         * @param resource Element identifier.
         *
         * @return Current specification builder.
         *
         * @since Espresso Macchiato 0.2
         */
        public Spec withRoot(int resource) {
            rootResource = resource;
            return this;
        }

        /**
         * Specify title view resource. (optional)
         *
         * @param resource Element identifier.
         *
         * @return Current specification builder.
         *
         * @since Espresso Macchiato 0.2
         */
        public Spec withTitle(int resource) {
            titleResource = resource;
            return this;
        }

        /**
         * Specify message view resource. (optional)
         *
         * @param resource Element identifier.
         *
         * @return Current specification builder.
         *
         * @since Espresso Macchiato 0.2
         */
        public Spec withMessage(int resource) {
            messageResource = resource;
            return this;
        }

        /**
         * Specify positive button resource. (optional)
         *
         * @param resource Element identifier.
         *
         * @return Current specification builder.
         *
         * @since Espresso Macchiato 0.2
         */
        public Spec withConfirmButton(int resource) {
            confirmButtonResource = resource;
            return this;
        }

        /**
         * Specify negative button resource. (optional)
         *
         * @param resource Element identifier.
         *
         * @return Current specification builder.
         *
         * @since Espresso Macchiato 0.2
         */
        public Spec withDenyButton(int resource) {
            denyButtonResource = resource;
            return this;
        }

        /**
         * Specify neutral button resource. (optional)
         *
         * @param resource Element identifier.
         *
         * @return Current specification builder.
         *
         * @since Espresso Macchiato 0.2
         */
        public Spec withCancelButton(int resource) {
            cancelButtonResource = resource;
            return this;
        }
    }

    /**
     * Create new element instance.
     *
     * @param spec Configure element with given specification.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspDialog(Spec spec) {
        super(spec.rootResource);
        this.spec = spec;
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspDialog(EspDialog template) {
        super(template);
        this.spec = template.spec;
    }

    /**
     * Access title element.
     *
     * @return New element for actions and assertions.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspTextView title() {
        return EspTextView.byId(spec.titleResource);
    }

    /**
     * Access message element.
     *
     * @return New element for actions and assertions.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspTextView message() {
        return EspTextView.byId(spec.messageResource);
    }

    /**
     * Access positive button element.
     *
     * @return New element for actions and assertions.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspButton confirmButton() {
        return EspButton.byId(spec.confirmButtonResource);
    }

    /**
     * Access negative button element.
     *
     * @return New element for actions and assertions.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspButton denyButton() {
        return EspButton.byId(spec.denyButtonResource);
    }

    /**
     * Access neutral button element.
     *
     * @return New element for actions and assertions.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspButton cancelButton() {
        return EspButton.byId(spec.cancelButtonResource);
    }

    /**
     * Dismiss dialog.
     *
     * This is like clicking outside of the dialog instead any dialog button.
     * If the dialog is not cancelable this call have no effect.
     *
     * @since Espresso Macchiato 0.4
     */
    public void dismiss() {
        Espresso.pressBack();
    }


    /**
     * @since Espresso Macchiato 0.2
     * @deprecated Currently not supported please use a different element creator.
     */
    @Deprecated // mark parent static method as not usable for this class
    public static EspView byId(int rootResource) {
        throw new UnsupportedOperationException();
    }
}
