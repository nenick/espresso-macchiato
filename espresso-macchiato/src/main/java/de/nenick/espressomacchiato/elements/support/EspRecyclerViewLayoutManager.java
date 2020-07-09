package de.nenick.espressomacchiato.elements.support;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;

import de.nenick.espressomacchiato.assertions.support.GridLayoutManagerColumnCountAssertion;

import static de.nenick.espressomacchiato.assertions.support.LayoutManagerTypeAssertion.isGridLayoutManager;
import static de.nenick.espressomacchiato.assertions.support.LayoutManagerTypeAssertion.isLinearLayoutManager;

/**
 * Assertions for RecyclerView layout manager.
 *
 * @since Espresso Macchiato 0.6
 */
public class EspRecyclerViewLayoutManager {

    private final ViewInteraction recyclerViewInteraction;

    /**
     * Create new element instance.
     *
     * @param recyclerViewInteraction prepared interaction for target {@link RecyclerView}
     * @since Espresso Macchiato 0.6
     */
    public EspRecyclerViewLayoutManager(ViewInteraction recyclerViewInteraction) {
        this.recyclerViewInteraction = recyclerViewInteraction;
    }

    /**
     * Check that the layout manager is of type {@link LinearLayoutManager}
     *
     * @since Espresso Macchiato 0.6
     */
    public void assertLinearLayoutManager() {
        recyclerViewInteraction.check(isLinearLayoutManager());
    }

    /**
     * Check that the layout manager is of type {@link GridLayoutManager}
     *
     * @since Espresso Macchiato 0.6
     */
    public void assertGridLayoutManager() {
        recyclerViewInteraction.check(isGridLayoutManager());
    }

    /**
     * Check that the {@link GridLayoutManager} has expected count of column.
     *
     * @param expectedColumnCount expected count of columns
     * @since Espresso Macchiato 0.6
     */
    public void assertColumnCount(int expectedColumnCount) {
        assertGridLayoutManager();
        recyclerViewInteraction.check(new GridLayoutManagerColumnCountAssertion(expectedColumnCount));
    }

}
