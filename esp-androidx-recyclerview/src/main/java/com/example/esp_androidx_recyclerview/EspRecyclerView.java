package com.example.esp_androidx_recyclerview;

import de.nenick.espressomacchiato.assertions.support.RecyclerViewItemCountAssertion;

/**
 * Actions and assertions for RecyclerView.
 *
 * @since Espresso Macchiato 0.5
 */
public class EspRecyclerView {

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
     * Access item by index known by the adapter.
     *
     * @param index Item index in adapter.
     *
     * @return New element instance for action and assertions.
     *
     * @since Espresso Macchiato 0.6
     */
    public EspRecyclerViewItem itemByIndex(int index) {
        return EspRecyclerViewItem.byItemIndex(baseMatcher(), index);
    }

    /**
     * Access the layout manager of this recycler view.
     *
     * @return New instance for action and assertions.
     *
     * @since Espresso Macchiato 0.6
     */
    public EspRecyclerViewLayoutManager layoutManager() {
        return new EspRecyclerViewLayoutManager(findView());
    }

    private int resourceId() {
        return resourceId;
    }
}
