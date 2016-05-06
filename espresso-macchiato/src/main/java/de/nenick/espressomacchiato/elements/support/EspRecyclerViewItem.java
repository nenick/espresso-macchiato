package de.nenick.espressomacchiato.elements.support;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.elements.EspView;
import de.nenick.espressomacchiato.matchers.support.EspRecyclerViewMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspRecyclerViewItem extends EspView {

    enum Mode {
        byItemIndex,
        byVisibleIndex
    }

    private int recyclerViewId;
    private int index;
    private Mode mode;

    public EspRecyclerViewItem(Matcher<View> base, int recyclerViewId, int index, Mode mode) {
        super(base);
        this.recyclerViewId = recyclerViewId;
        this.index = index;
        this.mode = mode;
    }

    public static EspRecyclerViewItem byItemIndex(int recyclerViewId, int index) {
        return new EspRecyclerViewItem(withId(recyclerViewId), recyclerViewId, index, Mode.byItemIndex);
    }

    public static EspRecyclerViewItem byVisibleIndex(int recyclerViewId, int index) {
        return new EspRecyclerViewItem(EspRecyclerViewMatcher.withRecyclerView(recyclerViewId).atChildIndex(index), recyclerViewId, index, Mode.byVisibleIndex);
    }

    public void scrollTo() {
        if (mode == Mode.byItemIndex) {
            findView(EspRecyclerViewMatcher.withMinimalItemCount(index + 1)).perform(RecyclerViewActions.scrollToPosition(index));
        } else {
            throw new UnsupportedOperationException("Method only supported when item accessed " + Mode.byItemIndex.name());
        }
    }

    protected Matcher<View> baseMatcherForItemChild(Matcher<View> matcher) {
        if (mode == Mode.byVisibleIndex) {
            return EspRecyclerViewMatcher.withRecyclerView(recyclerViewId).atChildIndexOnView(index, matcher);
        } else {
            throw new UnsupportedOperationException("Method only supported when item accessed " + Mode.byVisibleIndex.name());
        }
    }
}
