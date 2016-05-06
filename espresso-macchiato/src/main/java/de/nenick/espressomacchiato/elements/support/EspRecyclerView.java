package de.nenick.espressomacchiato.elements.support;

import de.nenick.espressomacchiato.assertions.support.RecyclerViewItemCountAssertion;
import de.nenick.espressomacchiato.elements.EspView;

public class EspRecyclerView extends EspView {

    protected int resourceId;

    public static EspRecyclerView byId(int resourceId) {
        return new EspRecyclerView(resourceId);
    }

    public EspRecyclerView(int resourceId) {
        super(resourceId);
        this.resourceId = resourceId;
    }

    public EspRecyclerView(EspRecyclerView template) {
        super(template.resourceId());
        this.resourceId = template.resourceId();
    }

    public void assertItemCountIs(final int expectedCount) {
        findView().check(new RecyclerViewItemCountAssertion(expectedCount));
    }

    public EspRecyclerViewItem itemByVisibleIndex(int index) {
        return EspRecyclerViewItem.byVisibleIndex(resourceId, index);
    }

    public EspRecyclerViewItem itemByItemIndex(int index) {
        return EspRecyclerViewItem.byItemIndex(resourceId, index);
    }

    public int resourceId() {
        return resourceId;
    }
}
