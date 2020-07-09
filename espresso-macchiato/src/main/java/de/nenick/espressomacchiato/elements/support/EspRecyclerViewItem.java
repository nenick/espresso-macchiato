package de.nenick.espressomacchiato.elements.support;

import androidx.annotation.Nullable;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

import de.nenick.espressomacchiato.assertions.support.LayoutManagerItemVisibilityAssertion;
import de.nenick.espressomacchiato.elements.EspView;
import de.nenick.espressomacchiato.matchers.support.EspRecyclerViewMatcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
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
     * @param recyclerView Matcher for adapter view containing this item.
     * @param index        Item index for doing actions or assertions.
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
     * @param base  Matcher for item parent (RecyclerView).
     * @param index Item index for doing actions or assertions.
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
     * When you use CoordinatorLayout and it is expanded then the item may not be full displayed.
     * This happen because the list is partly moved outside of the screen through CoordinatorLayout.
     * You can workaround this issue by calling {@link EspAppBarLayout#collapse()}.
     *
     * @since Espresso Macchiato 0.5
     */
    public void scrollTo() {
        // check first if adapter has enough items to avoid strange error messages
        Matcher<View> itemCountMatcher = EspRecyclerViewMatcher.withMinimalAdapterItemCount(index + 1);
        findRecyclerView(itemCountMatcher).perform(RecyclerViewActions.scrollToPosition(index));
    }

    /**
     * Check that this item is not displayed on screen.
     *
     * Is true when the item exist but not displayed so you must scroll to it.
     *
     * @since Espresso Macchiato 0.6
     */
    @Override
    public void assertIsHidden() {
        findRecyclerView().check(LayoutManagerItemVisibilityAssertion.isHidden(index));
    }

    /**
     * Check that this item exist.
     *
     * Is true when adapter has matching item ignores the display state.
     *
     * @since Espresso Macchiato 0.6
     */

    public void assertExist() {
        findRecyclerView().check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException != null) {
                    throw noViewFoundException;
                }

                RecyclerView recyclerView = (RecyclerView) view;
                if (index >= recyclerView.getAdapter().getItemCount()) {
                    throw new AssertionFailedError("Requested item should exist.");
                }
            }
        });
    }

    /**
     * Check that this item is not displayed on screen.
     *
     * Is true when the no matching item exist in adapter.
     *
     * @since Espresso Macchiato 0.6
     */
    @Override
    public void assertNotExist() {
        onView(EspRecyclerViewMatcher.withRecyclerView(recyclerViewMatcher)
                .ignoreItemNotFound(true).atIndex(index)
        ).check(doesNotExist());
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

    /**
     * Create an interaction to perform assertion and actions on recycler view.
     *
     * @return Interaction for assertion and actions.
     *
     * @since Espresso Macchiato 0.6
     */
    protected ViewInteraction findRecyclerView() {
        return onView(baseMatcher());
    }

    /**
     * Create an interaction to perform assertion and actions on recycler view.
     *
     * @param additional More specification about the requested element.
     *
     * @return Interaction for assertion and actions.
     *
     * @since Espresso Macchiato 0.6
     */
    protected ViewInteraction findRecyclerView(Matcher<View> additional) {
        return onView(allOf(baseMatcher(), additional));
    }

    /**
     * Create an interaction to perform assertion and actions on list items.
     *
     * @param additional More specification about the requested element.
     *
     * @return Interaction for assertion and actions.
     */
    @Override
    protected ViewInteraction findView(List<Matcher<View>> additional) {
        ArrayList<Matcher<? super View>> allMatcher = new ArrayList<>();
        allMatcher.add(baseMatcherForItemChild(null));
        allMatcher.addAll(additional);
        return onView(allOf(allMatcher));
    }
}
