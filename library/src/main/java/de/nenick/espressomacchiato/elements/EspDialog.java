package de.nenick.espressomacchiato.elements;

public class EspDialog extends EspView {

    protected Spec spec;

    /** hide {@link EspView#byId(int)} */
    @Deprecated
    public static EspView byId(int rootResource) {
        throw new UnsupportedOperationException();
    }

    public static Spec spec() {
        return new Spec();
    }

    static class Spec {

        private int rootResource;
        private int titleResource;
        private int messageResource;
        private int confirmButtonResource;
        private int denyButtonResource;
        private int cancelButtonResource;

        public EspDialog build() {
            return new EspDialog(this);
        }

        public Spec withRoot(int resource) {
            rootResource = resource;
            return this;
        }

        public Spec withTitle(int resource) {
            titleResource = resource;
            return this;
        }

        public Spec withMessage(int resource) {
            messageResource = resource;
            return this;
        }

        public Spec withConfirmButton(int resource) {
            confirmButtonResource = resource;
            return this;
        }

        public Spec withDenyButton(int resource) {
            denyButtonResource = resource;
            return this;
        }

        public Spec withCancelButton(int resource) {
            cancelButtonResource = resource;
            return this;
        }
    }

    public EspDialog(Spec spec) {
        super(spec.rootResource);
        this.spec = spec;
    }

    public EspTextView title() {
        return EspTextView.byId(spec.titleResource);
    }

    public EspTextView message() {
        return EspTextView.byId(spec.messageResource);
    }

    public EspButton confirmButton() {
        return EspButton.byId(spec.confirmButtonResource);
    }

    public EspButton denyButton() {
        return EspButton.byId(spec.denyButtonResource);
    }

    public EspButton cancelButton() {
        return EspButton.byId(spec.cancelButtonResource);
    }
}
