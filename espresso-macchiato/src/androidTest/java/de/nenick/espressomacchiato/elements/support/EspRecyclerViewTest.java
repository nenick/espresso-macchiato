package de.nenick.espressomacchiato.elements.support;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongRecyclerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class EspRecyclerViewTest extends EspressoTestCase<LongRecyclerActivity> {
    private EspRecyclerView espRecyclerView = EspRecyclerView.byId(LongRecyclerActivity.recyclerViewId);

    @Test
    public void testById() {
        espRecyclerView.assertIsVisible();
    }

    @Test
    public void testCustomBaseMatcher() {
        espRecyclerView = new EspRecyclerView(allOf(withId(LongRecyclerActivity.recyclerViewId), isDisplayed()));
        espRecyclerView.assertIsVisible();
    }

    @Test
    public void testAssertItemCountIs() {
        espRecyclerView.assertItemCountIs(LongRecyclerActivity.recyclerViewItemCount);
    }
}