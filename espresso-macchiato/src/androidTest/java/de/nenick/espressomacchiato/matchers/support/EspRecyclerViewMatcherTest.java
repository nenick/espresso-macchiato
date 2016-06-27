package de.nenick.espressomacchiato.matchers.support;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongRecyclerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertFalse;

/** Basic test */
public class EspRecyclerViewMatcherTest extends EspressoTestCase<LongRecyclerActivity> {

    @Test
    public void testAtChildIndexOnViewOnlyMatchRecyclerView() {
        Matcher<View> itemCountMatcher = EspRecyclerViewMatcher.withRecyclerView(withId(LongRecyclerActivity.recyclerViewId)).atChildIndex(0);
        View nonRecyclerView = new View(InstrumentationRegistry.getContext());
        assertFalse(itemCountMatcher.matches(nonRecyclerView));
    }

    @Test
    public void testWithMinimalItemCountOnlyMatchRecyclerView() {
        Matcher<View> itemCountMatcher = EspRecyclerViewMatcher.withMinimalAdapterItemCount(10);
        View nonRecyclerView = new View(InstrumentationRegistry.getContext());
        assertFalse(itemCountMatcher.matches(nonRecyclerView));
    }
}