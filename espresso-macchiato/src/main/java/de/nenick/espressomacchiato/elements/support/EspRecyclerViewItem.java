package de.nenick.espressomacchiato.elements.support;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.elements.EspView;
import de.nenick.espressomacchiato.matchers.support.EspRecyclerViewMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Actions and assertions for RecyclerView items.
 *
 * @since Espresso Macchiato 0.5
 */
public class EspRecyclerViewItem extends EspView {

    enum Mode {
        byItemIndex,
        byVisibleIndex
    }

    private int recyclerViewId;
    private int index;
    private Mode mode;

    /**
     * Create new instance based on adapter index.
     *
     * @param recyclerViewId Adapter view containing this item.
     * @param index          Item index for doing actions or assertions.
     *
     * @return New instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.5
     */
    public static EspRecyclerViewItem byItemIndex(int recyclerViewId, int index) {
        return new EspRecyclerViewItem(withId(recyclerViewId), recyclerViewId, index, Mode.byItemIndex);
    }

    /**
     * Create new instance based on visible index.
     *
     * @param recyclerViewId Adapter view containing this item.
     * @param index          Item index for doing actions or assertions.
     *
     * @return New instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.5
     */
    public static EspRecyclerViewItem byVisibleIndex(int recyclerViewId, int index) {
        return new EspRecyclerViewItem(EspRecyclerViewMatcher.withRecyclerView(recyclerViewId).atChildIndex(index), recyclerViewId, index, Mode.byVisibleIndex);
    }

    /**
     * Create new instance.
     *
     * @param base           For {@link Mode#byItemIndex} use RecyclerView base matcher.
     *                       For {@link Mode#byVisibleIndex} use EspRecyclerViewMatcher.
     * @param recyclerViewId Adapter view containing this item.
     * @param index          Item index for doing actions or assertions.
     * @param mode           Choose an index {@link Mode}.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspRecyclerViewItem(Matcher<View> base, int recyclerViewId, int index, Mode mode) {
        super(base);
        this.recyclerViewId = recyclerViewId;
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
    public EspRecyclerViewItem(EspRecyclerViewItem template) {
        super(template.baseMatcher());
        this.recyclerViewId = template.recyclerViewId;
        this.index = template.index;
        this.mode = template.mode;
    }

    /**
     * Make the item visible.
     *
     * Does only work when this item is accessed by adapter index.
     *
     * @since Espresso Macchiato 0.5
     */
    public void scrollTo() {
        if (mode == Mode.byItemIndex) {
            findView(EspRecyclerViewMatcher.withMinimalItemCount(index + 1)).perform(RecyclerViewActions.scrollToPosition(index));
        } else {
            throw new UnsupportedOperationException("Method only supported when item accessed " + Mode.byItemIndex.name());
        }
    }

    /**
     * Base for matching a view inside this item.
     *
     * Does only work when this item is accessed by visible index.
     *
     * @param matcher Matcher for any item child view.
     *
     * @return Child view matcher for accessed item.
     *
     * @since Espresso Macchiato 0.5
     */
    protected Matcher<View> baseMatcherForItemChild(Matcher<View> matcher) {
        if (mode == Mode.byVisibleIndex) {
            return EspRecyclerViewMatcher.withRecyclerView(recyclerViewId).atChildIndexOnView(index, matcher);
        } else {
            throw new UnsupportedOperationException("Method only supported when item accessed " + Mode.byVisibleIndex.name());
        }
    }
}
