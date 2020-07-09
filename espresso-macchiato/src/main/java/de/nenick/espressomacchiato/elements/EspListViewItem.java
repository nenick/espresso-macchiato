package de.nenick.espressomacchiato.elements;

import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.matchers.EspAdapterViewItemMatcher;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Actions and assertions for ListView items including ChildViews and AdapterItems.
 *
 * @since Espresso Macchiato 0.3
 */
public class EspListViewItem extends EspAdapterViewItem {

    /**
     * Create new instance based on adapter index.
     *
     * @param adapterViewId Adapter view containing this item.
     * @param index         Item index for doing actions or assertions.
     *
     * @return New instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.5
     */
    public static EspListViewItem byItemIndex(int adapterViewId, int index) {
        return new EspListViewItem(withId(adapterViewId), adapterViewId, index, Mode.byItemIndex);
    }

    /**
     * Create new instance based on visible index.
     *
     * @param adapterViewId Adapter view containing this item.
     * @param index         Item index for doing actions or assertions.
     *
     * @return New instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.5
     */
    public static EspListViewItem byVisibleIndex(int adapterViewId, int index) {
        return new EspListViewItem(EspAdapterViewItemMatcher.withAdapterView(withId(adapterViewId)).atChildIndex(index), adapterViewId, index, Mode.byVisibleIndex);
    }

    /**
     * Create new instance.
     *
     * @param listMatcher For {@link Mode#byItemIndex} use AdapterView base matcher.
     *                    For {@link Mode#byVisibleIndex} use EspAdapterViewItemMatcher.
     * @param listViewId  Adapter view containing this item.
     * @param index       Item index for doing actions or assertions.
     * @param mode        Choose an index {@link Mode}.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspListViewItem(Matcher<View> listMatcher, int listViewId, int index, Mode mode) {
        super(listMatcher, listViewId, index, mode);
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspListViewItem(EspAdapterViewItem template) {
        super(template);
    }

/*
    public static EspListViewItem byText(String text, String dataSourceField) {
        return new EspListViewItem(text, dataSourceField);
    }

    public EspListViewItem(String itemText, String dataSourceField) {
        super(itemText, dataSourceField);
    }
*/
}
