package de.nenick.espressomacchiato.elements;

import de.nenick.espressomacchiato.assertions.AdapterViewItemCountAssertion;

/**
 * Base for actions and assertions for a AdapterView.
 *
 * Recommend is to use more specific elements like {@link EspListView}.
 * For actions and assertions with AdapterView items see {@link EspAdapterViewItem}
 *
 * @since Espresso Macchiato 0.3
 */
public class EspAdapterView extends EspView {

    private int resourceId;

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @return New element instance for action and assertions.
     *
     * @since Espresso Macchiato 0.3
     */
    public static EspAdapterView byId(int resourceId) {
        return new EspAdapterView(resourceId);
    }

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @since Espresso Macchiato 0.3
     */
    public EspAdapterView(int resourceId) {
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
    public EspAdapterView(EspAdapterView template) {
        super(template);
        resourceId = template.resourceId;
    }

    /**
     * Check that the views adapter contains the expected count of items.
     *
     * This counts the items existing in adapter and not the visible items.
     * Header and footer are count as list items.
     *
     * @param expected Zero for no items or a positive number.
     *
     * @since Espresso Macchiato 0.3
     */
    public void assertItemCountIs(int expected) {
        findView().check(new AdapterViewItemCountAssertion(expected));
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
    public EspAdapterViewItem itemByIndex(int index) {
        return EspAdapterViewItem.byItemIndex(resourceId, index);
    }

    /**
     * Access item by his visible index.
     *
     * @param index That is the index which a human can see.
     *
     * @return New element instance for action and assertions.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspAdapterViewItem itemByVisibleIndex(int index) {
        return EspAdapterViewItem.byVisibleIndex(resourceId, index);
    }
}
