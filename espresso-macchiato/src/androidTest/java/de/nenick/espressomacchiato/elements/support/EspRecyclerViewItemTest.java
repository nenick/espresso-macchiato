package de.nenick.espressomacchiato.elements.support;

import android.content.Intent;
import androidx.test.espresso.NoMatchingViewException;
import android.view.View;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matcher;
import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.test.views.LongRecyclerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

/** Basic tests */
public class EspRecyclerViewItemTest extends EspressoTestCase<LongRecyclerActivity> {

    public static final int ITEM_INDEX_INITIAL_DISPLAYED = 0;
    public static final int ITEM_INDEX_INITIAL_NOT_DISPLAYED = 30;

    private final Matcher<View> recyclerViewMatcher = withId(LongRecyclerActivity.recyclerViewId);
    private EspRecyclerViewItem itemInitialDisplayed = EspRecyclerViewItem.byItemIndex(recyclerViewMatcher, ITEM_INDEX_INITIAL_DISPLAYED);
    private EspRecyclerViewItem itemInitialNotDisplayed = EspRecyclerViewItem.byItemIndex(recyclerViewMatcher, ITEM_INDEX_INITIAL_NOT_DISPLAYED);
    private EspRecyclerViewItem itemInitialNotExist = EspRecyclerViewItem.byItemIndex(recyclerViewMatcher, LongRecyclerActivity.recyclerViewItemCount);

    @Test
    public void testBaseMatcherForItemChild() {
        EspTextView espTextView = new EspTextView(itemInitialDisplayed.baseMatcherForItemChild(withId(LongRecyclerActivity.itemTextViewId)));
        espTextView.assertTextIs("item: 0");
    }

    @Test
    public void testBaseMatcherForItemChildFailureWhenItemIsNotDisplayed() {
        EspTextView espTextView = new EspTextView(itemInitialNotDisplayed.baseMatcherForItemChild(withId(LongRecyclerActivity.itemTextViewId)));

        exception.expect(AssertionFailedError.class);
        exception.expectMessage("Requested item is currently not displayed. Try first scrollTo() to make the item visible.");

        espTextView.assertTextIs("item: 0");
    }

    @Test
    public void testScrollTo() {
        itemInitialNotDisplayed.scrollTo();
        EspTextView espTextView = new EspTextView(itemInitialNotDisplayed.baseMatcherForItemChild(withId(LongRecyclerActivity.itemTextViewId)));
        espTextView.assertTextIs("item: 30");
    }

    @Test
    public void testScrollToFailureWhenLessItems() {
        exception.expect(NoMatchingViewException.class);
        exception.expectMessage("No views in hierarchy found matching: (with id: de.nenick.espressomacchiato.test:id/list and Adapter has minimal <101> items (to access requested index <100>)");
        itemInitialNotExist.scrollTo();
    }

    @Test
    public void testMatcherFailureWhenRecyclerViewNotExist() {
        getActivity().startActivity(new Intent(getActivity(), BaseActivity.class));

        exception.expect(NoMatchingViewException.class);
        exception.expectMessage("No views in hierarchy found matching: (with id: de.nenick.espressomacchiato.test:id/list and Adapter has minimal <1> items (to access requested index <0>)");

        itemInitialDisplayed.scrollTo();
    }

    @Test
    public void testMatcherFailureWhenChildViewNotMatch() {
        EspTextView espTextView = new EspTextView(itemInitialDisplayed.baseMatcherForItemChild(withId(LongRecyclerActivity.itemTextViewId)));
        exception.expect(AssertionFailedError.class);
        exception.expectMessage("'with text: is \"item: 20\"' doesn't match the selected view");
        espTextView.assertTextIs("item: 20");
    }

    @Test
    public void testTemplateConstructor() {
        MyEspRecyclerViewItem myEspRecyclerViewItem = new MyEspRecyclerViewItem(EspRecyclerViewItem.byItemIndex(recyclerViewMatcher, ITEM_INDEX_INITIAL_DISPLAYED));
        myEspRecyclerViewItem.assertIsDisplayedOnScreen();
    }

    @Test
    public void testAssertIsDisplayedOnScreen() {
        itemInitialDisplayed.assertIsDisplayedOnScreen();
    }

    @Test
    public void testAssertIsDisplayedOnScreenFailure() {
        exception.expect(AssertionFailedError.class);
        exception.expectMessage("Requested item is currently not displayed. Try first scrollTo() to make the item visible.");
        itemInitialNotDisplayed.assertIsDisplayedOnScreen();
    }

    @Test
    public void testAssertIsDisplayedOnScreenAfterScroll() {
        itemInitialNotDisplayed.scrollTo();
        itemInitialNotDisplayed.assertIsDisplayedOnScreen();
    }

    @Test
    public void testAssertIsHidden() {
        itemInitialNotDisplayed.assertIsHidden();
    }

    @Test
    public void testAssertIsHiddenFailed() {
        exception.expect(AssertionFailedError.class);
        exception.expectMessage("expected item 0 to not be visible, but was visible");
        itemInitialDisplayed.assertIsHidden();
    }

    @Test
    public void testAssertNotExist() {
        itemInitialNotExist.assertNotExist();
    }

    @Test
    public void testAssertNotExistFailed() {
        exception.expect(AssertionFailedError.class);
        exception.expectMessage("View is present in the hierarchy:");
        itemInitialDisplayed.assertNotExist();
    }

    @Test
    public void testAssertExist() {
        itemInitialNotDisplayed.assertExist();
    }

    @Test
    public void testAssertExistFailed() {
        exception.expect(AssertionFailedError.class);
        exception.expectMessage("Requested item should exist.");
        itemInitialNotExist.assertExist();
    }

    /** Element extension */
    class MyEspRecyclerViewItem extends EspRecyclerViewItem {

        public MyEspRecyclerViewItem(EspRecyclerViewItem template) {
            super(template);
        }
    }
}