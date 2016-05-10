package de.nenick.espressomacchiato.elements;

public class EspListView extends EspAdapterView {

    public EspListView(EspListView template) {
        super(template);
    }

    public static EspListView byId(int resourceId) {
        return new EspListView(resourceId);
    }

    public EspListView(int resourceId) {
        super(resourceId);
    }
}
