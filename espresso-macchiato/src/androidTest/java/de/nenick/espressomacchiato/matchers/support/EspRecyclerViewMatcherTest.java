package de.nenick.espressomacchiato.matchers.support;

import android.support.test.InstrumentationRegistry;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongRecyclerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.junit.Assert.assertFalse;

public class EspRecyclerViewMatcherTest extends EspressoTestCase<LongRecyclerActivity> {

    @Test
    public void testAtChildIndexOnViewOnlyMatchRecyclerView() {
        Matcher<View> itemCountMatcher = EspRecyclerViewMatcher.withRecyclerView(LongRecyclerActivity.recyclerViewId).atChildIndex(0);
        View nonRecyclerView = new View(InstrumentationRegistry.getContext());
        assertFalse(itemCountMatcher.matches(nonRecyclerView));
    }

    @Test
    public void testWithMinimalItemCountOnlyMatchRecyclerView() {
        Matcher<View> itemCountMatcher = EspRecyclerViewMatcher.withMinimalItemCount(10);
        View nonRecyclerView = new View(InstrumentationRegistry.getContext());
        assertFalse(itemCountMatcher.matches(nonRecyclerView));
    }
}