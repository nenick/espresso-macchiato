package de.nenick.espressomacchiato.elements.support;

import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;

import junit.framework.AssertionFailedError;

import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.test.views.LongRecyclerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspRecyclerViewItemByVisibleIndexTest extends EspressoTestCase<LongRecyclerActivity> {
    private EspRecyclerView espRecyclerView = EspRecyclerView.byId(LongRecyclerActivity.recyclerViewId);

    @Test
    public void testItemChildMatcher() {
        EspRecyclerViewItem item = espRecyclerView.itemByVisibleIndex(0);
        EspTextView espTextView = new EspTextView(item.baseMatcherForItemChild(withId(LongRecyclerActivity.itemTextViewId)));
        espTextView.assertTextIs("item: 0");
    }

    @Test
    public void testMatcherFailureWhenRecyclerViewHasLessChildrenAsRequestedIndex() {
        exception.expect(AssertionFailedError.class);
        exception.expectMessage("Requested child at index 20 but recycler view has only ");
        exception.expectMessage(" visible childs.");

        EspTextView espTextView = new EspTextView(espRecyclerView.itemByVisibleIndex(20).baseMatcherForItemChild(withId(LongRecyclerActivity.itemTextViewId)));
        espTextView.assertTextIs("item: 20");
    }

    @Test
    public void testMatcherFailureWhenChildViewNotMatch() {
        exception.expect(AssertionFailedError.class);
        exception.expectMessage("'with text: is \"item: 20\"' doesn't match the selected view");

        EspTextView espTextView = new EspTextView(espRecyclerView.itemByVisibleIndex(0).baseMatcherForItemChild(withId(LongRecyclerActivity.itemTextViewId)));
        espTextView.assertTextIs("item: 20");
    }

    @Test
    public void testMatcherFailureWhenRecyclerViewNotExist() {
        exception.expect(NoMatchingViewException.class);
        exception.expectMessage("No views in hierarchy found matching: (with id: de.nenick.espressomacchiato.test:id/list)");

        getActivity().startActivity(new Intent(getActivity(), BaseActivity.class));

        EspTextView espTextView = new EspTextView(espRecyclerView.itemByVisibleIndex(0).baseMatcherForItemChild(withId(LongRecyclerActivity.itemTextViewId)));
        espTextView.assertTextIs("item: 20");
    }

    @Test
    public void testScrollToFailureNotSupported() {
        exception.expect(UnsupportedOperationException.class);
        exception.expectMessage("Method only supported when item accessed byItemIndex");

        espRecyclerView.itemByVisibleIndex(20).scrollTo();
    }
}