package de.nenick.espressomacchiato.elements.support;

import de.nenick.espressomacchiato.assertions.support.RecyclerViewItemCountAssertion;
import de.nenick.espressomacchiato.elements.EspView;

/**
 * Actions and assertions for RecyclerView.
 *
 * @since Espresso Macchiato 0.5
 */
public class EspRecyclerView extends EspView {

    protected int resourceId;

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @since Espresso Macchiato 0.5
     */
    public static EspRecyclerView byId(int resourceId) {
        return new EspRecyclerView(resourceId);
    }

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspRecyclerView(int resourceId) {
        super(resourceId);
        this.resourceId = resourceId;
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspRecyclerView(EspRecyclerView template) {
        super(template.resourceId());
        this.resourceId = template.resourceId();
    }

    /**
     * Check that the views adapter contains the expected count of items.
     *
     * This counts the items existing in adapter and not the visible items.
     *
     * @param expectedCount Zero for no items or a positive number.
     *
     * @since Espresso Macchiato 0.5
     */
    public void assertItemCountIs(final int expectedCount) {
        findView().check(new RecyclerViewItemCountAssertion(expectedCount));
    }

    /**
     * Access item by his visible index.
     *
     * @param index That is the index which a human can see. First item is at index 0.
     *
     * @return New element instance for action and assertions.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspRecyclerViewItem itemByVisibleIndex(int index) {
        return EspRecyclerViewItem.byVisibleIndex(resourceId, index);
    }

    /**
     * Access item by index known by the adapter.
     *
     * @param index Item index in adapter.
     *
     * @return New element instance for action and assertions.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspRecyclerViewItem itemByItemIndex(int index) {
        return EspRecyclerViewItem.byItemIndex(resourceId, index);
    }

    private int resourceId() {
        return resourceId;
    }
}
