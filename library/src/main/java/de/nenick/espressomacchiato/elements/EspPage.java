package de.nenick.espressomacchiato.elements;

public class EspPage extends EspView {

    public static EspPage byId(int resourceId) {
        return new EspPage(resourceId);
    }

    public EspPage(int resourceId) {
        super(resourceId);
    }
}
