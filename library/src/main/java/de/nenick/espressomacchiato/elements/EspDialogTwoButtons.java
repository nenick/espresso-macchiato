package de.nenick.espressomacchiato.elements;

public class EspDialogTwoButtons extends EspDialogOneButton {

    private int denyButtonResource;

    public EspDialogTwoButtons(int rootResource, int titleResource, int messageResource, int confirmButtonResource, int denyButtonResource) {
        super(rootResource, titleResource, messageResource, confirmButtonResource);
        this.denyButtonResource = denyButtonResource;
    }

    public EspButton denyButton() {
        return EspButton.byId(denyButtonResource);
    }
}
