package de.nenick.espressomacchiato.elements;

public class EspDialog extends EspView {

    public static EspDialogOneButton bySpec(int rootResource, int titleResource, int messageResource, int confirmButtonResource) {
        return new EspDialogOneButton(rootResource, titleResource, messageResource, confirmButtonResource);
    }

    public static EspDialogTwoButtons bySpec(int rootResource, int titleResource, int messageResource, int confirmButtonResource, int denyButtonResource) {
        return new EspDialogTwoButtons(rootResource, titleResource, messageResource, confirmButtonResource, denyButtonResource);
    }

    public static EspDialogThreeButtons bySpec(int rootResource, int titleResource, int messageResource, int confirmButtonResource, int denyButtonResource, int cancelButtonResource) {
        return new EspDialogThreeButtons(rootResource, titleResource, messageResource, confirmButtonResource, denyButtonResource, cancelButtonResource);
    }

    public EspDialog(int rootResource) {
        super(rootResource);
    }
}
