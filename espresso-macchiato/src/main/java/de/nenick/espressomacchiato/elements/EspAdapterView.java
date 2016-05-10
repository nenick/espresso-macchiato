package de.nenick.espressomacchiato.elements;

import de.nenick.espressomacchiato.assertions.AdapterViewItemCountAssertion;

public class EspAdapterView extends EspView {

    private int resourceId;

    public EspAdapterView(EspAdapterView template) {
        super(template);
        resourceId = template.resourceId;
    }

    public static EspAdapterView byId(int resourceId) {
        return new EspAdapterView(resourceId);
    }

    public EspAdapterView(int resourceId) {
        super(resourceId);
        this.resourceId = resourceId;
    }

    public void assertItemCountIs(int expected) {
        findView().check(new AdapterViewItemCountAssertion(expected));
    }

    public EspAdapterViewItem itemByIndex(int index) {
        return EspAdapterViewItem.byItemIndex(resourceId, index);
    }

    public EspAdapterViewItem itemByVisibleIndex(int index) {
        return EspAdapterViewItem.byVisibleIndex(resourceId, index);
    }
}
