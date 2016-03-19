package de.nenick.espressomacchiato.elements;

public class EspDialogThreeButtons extends EspDialogTwoButtons {

    private final int cancelButtonResource;

    public EspDialogThreeButtons(int rootResource, int titleResource, int messageResource, int confirmButtonResource, int denyButtonResource, int cancelButtonResource) {
        super(rootResource, titleResource, messageResource, confirmButtonResource, denyButtonResource);
        this.cancelButtonResource = cancelButtonResource;
    }

    public EspButton cancelButton() {
        return EspButton.byId(cancelButtonResource);
    }
}
