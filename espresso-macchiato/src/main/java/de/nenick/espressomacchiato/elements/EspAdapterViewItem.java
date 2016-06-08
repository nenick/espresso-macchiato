package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.Map;

import de.nenick.espressomacchiato.matchers.EspAdapterViewItemMatcher;
import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Actions and assertions for AdapterView items including ChildViews and AdapterItems.
 *
 * Some actions and assertions can only be used when accessing the item by visible index or adapter index.
 * Recommend is to use more specific elements like {@link EspListViewItem}.
 *
 * @since Espresso Macchiato 0.3
 */
public class EspAdapterViewItem extends EspView {

    private int adapterViewId;
    private int index;
    private Mode mode;
    //private String property;
    //private String itemText;

    enum Mode {
        byItemIndex,
        byVisibleIndex
    }

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
    public static EspAdapterViewItem byItemIndex(int adapterViewId, int index) {
        return new EspAdapterViewItem(withId(adapterViewId), adapterViewId, index, Mode.byItemIndex);
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
    public static EspAdapterViewItem byVisibleIndex(int adapterViewId, int index) {
        return new EspAdapterViewItem(EspAdapterViewItemMatcher.withAdapterView(withId(adapterViewId)).atChildIndex(index), adapterViewId, index, Mode.byVisibleIndex);
    }

    /*
        public static EspAdapterViewItem byText(String text, String property) {
            return new EspAdapterViewItem(text, property);
        }

        public EspAdapterViewItem(String itemText, String dataSourceField) {
            super(withText(itemText));
            this.itemText = itemText;
            this.property = dataSourceField;
        }
    */

    /**
     * Create new instance.
     *
     * @param base          For {@link Mode#byItemIndex} use AdapterView base matcher.
     *                      For {@link Mode#byVisibleIndex} use EspAdapterViewItemMatcher.
     * @param adapterViewId Adapter view containing this item.
     * @param index         Item index for doing actions or assertions.
     * @param mode          Choose an index {@link Mode}.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspAdapterViewItem(Matcher<View> base, int adapterViewId, int index, Mode mode) {
        super(base);
        this.adapterViewId = adapterViewId;
        this.index = index;
        this.mode = mode;
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspAdapterViewItem(EspAdapterViewItem template) {
        super(template);
        adapterViewId = template.adapterViewId;
        index = template.index;
        mode = template.mode;
    }

    /**
     * Make the item visible.
     *
     * Does only work when this item is accessed by adapter index.
     *
     * @since Espresso Macchiato 0.3
     */
    public void scrollTo() {
        if (mode == Mode.byItemIndex) {
            onData(instanceOf(Map.class)).atPosition(index).check(matches(ViewMatchers.isCompletelyDisplayed()));
            //onRow(itemText, property).check(matches(isCompletelyDisplayed()));
        } else {
            throw new UnsupportedOperationException("Method only supported when item accessed byItemIndex");
        }
    }

    /*
        protected DataInteraction onRow(Object value, String property) {
            return onData(hasEntry(equalTo(property), is(value)));
        }
    */

    /**
     * Base for matching a view inside this item.
     *
     * Does only work when this item is accessed by visible index.
     *
     * @param childMatcher Matcher for any item child view.
     *
     * @return Child view matcher for accessed item.
     *
     * @since Espresso Macchiato 0.5
     */
    protected Matcher<View> baseMatcherForItemChild(final Matcher<View> childMatcher) {
        if (mode == Mode.byVisibleIndex) {
            return EspAdapterViewItemMatcher.withAdapterView(withId(adapterViewId)).atChildIndexOnView(index, childMatcher);
        } else {
            throw new UnsupportedOperationException("Method only supported when item accessed byVisibleIndex");
        }
    }

    /**
     * @since Espresso Macchiato 0.4
     * @deprecated Currently not supported please use a different element creator.
     */
    @Deprecated
    public static EspAllOfBuilder<EspView> byAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * @since Espresso Macchiato 0.3
     * @deprecated Currently not supported please use a different element creator.
     */
    @Deprecated
    public static EspView byId(int resourceId) {
        throw new UnsupportedOperationException();
    }
}
