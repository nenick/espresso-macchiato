package de.nenick.espressomacchiato.elements.support;

import android.support.test.espresso.ViewInteraction;

import de.nenick.espressomacchiato.assertions.support.GridLayoutManagerColumnCountAssertion;
import de.nenick.espressomacchiato.assertions.support.LayoutManagerItemVisibilityAssertion;

import static de.nenick.espressomacchiato.assertions.support.LayoutManagerTypeAssertion.isGridLayoutManager;

/**
 * Assertions for RecyclerView layout manager.
 *
 * @since Espresso Macchiato 0.6
 */
public class EspRecyclerViewLayoutManager {

    private final ViewInteraction recyclerViewInteraction;

    public EspRecyclerViewLayoutManager(ViewInteraction recyclerViewInteraction) {
        this.recyclerViewInteraction = recyclerViewInteraction;
    }

    public void assertGridLayoutManager() {
        recyclerViewInteraction.check(isGridLayoutManager());
    }

    public void assertColumnCount(int expectedColumnCount) {
        assertGridLayoutManager();
        recyclerViewInteraction.check(new GridLayoutManagerColumnCountAssertion(expectedColumnCount));
    }

    public void assertItemIsVisible(int index) {
        recyclerViewInteraction.check(new LayoutManagerItemVisibilityAssertion(index, true));
    }

    public void assertItemIsNotVisible(int index) {
        recyclerViewInteraction.check(new LayoutManagerItemVisibilityAssertion(index, false));
    }

}
