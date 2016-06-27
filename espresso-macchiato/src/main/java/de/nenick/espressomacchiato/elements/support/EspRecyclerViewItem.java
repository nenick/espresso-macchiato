package de.nenick.espressomacchiato.elements.support;

import android.support.annotation.Nullable;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

import de.nenick.espressomacchiato.elements.EspView;
import de.nenick.espressomacchiato.matchers.support.EspRecyclerViewMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Actions and assertions for RecyclerView items.
 *
 * @since Espresso Macchiato 0.5
 */
public class EspRecyclerViewItem extends EspView {

    private final Matcher<View> recyclerViewMatcher;
    private int index;

    /**
     * Create new instance based on adapter index.
     *
     * @param recyclerView   Matcher for adapter view containing this item.
     * @param index          Item index for doing actions or assertions.
     *
     * @return New instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.5
     */
    public static EspRecyclerViewItem byItemIndex(Matcher<View> recyclerView, int index) {
        return new EspRecyclerViewItem(recyclerView, index);
    }

    /**
     * Create new instance.
     *
     * @param base           Matcher for item parent (RecyclerView).
     * @param index          Item index for doing actions or assertions.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspRecyclerViewItem(Matcher<View> base, int index) {
        super(base);
        recyclerViewMatcher = base;
        this.index = index;
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspRecyclerViewItem(EspRecyclerViewItem template) {
        super(template);
        this.recyclerViewMatcher = template.baseMatcher();
        this.index = template.index;
    }

    /**
     * Make the item visible.
     *
     * @since Espresso Macchiato 0.5
     */
    public void scrollTo() {
        // check first if adapter has enough items to avoid strange error messages
        Matcher<View> itemCountMatcher = EspRecyclerViewMatcher.withMinimalAdapterItemCount(index + 1);
        findRecyclerView(itemCountMatcher).perform(RecyclerViewActions.scrollToPosition(index));
    }

    /**
     * Base for matching a view inside this item.
     *
     * @param matcher Matcher for any item child view.
     *
     * @return Child view matcher for accessed item.
     *
     * @since Espresso Macchiato 0.5
     */
    protected Matcher<View> baseMatcherForItemChild(@Nullable Matcher<View> matcher) {
        return EspRecyclerViewMatcher.withRecyclerView(recyclerViewMatcher).atChildIndexOnView(index, matcher);
    }

    protected ViewInteraction findRecyclerView(Matcher<View> additional) {
        return super.findView(createMatcherList(additional));
    }

    @Override
    protected ViewInteraction findView(List<Matcher<View>> additional) {
        ArrayList<Matcher<? super View>> allMatcher = new ArrayList<>();
        allMatcher.add(baseMatcherForItemChild(null));
        allMatcher.addAll(additional);
        return onView(allOf(allMatcher));
    }
}
