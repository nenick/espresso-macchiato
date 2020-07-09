package de.nenick.espressomacchiato.elements;

import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.AssertionFailedError;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

/** Basic tests */
public class EspViewListSupportedTest extends EspressoTestCase<LongListActivity> {

    private EspPage espPage = EspPage.byId(LongListActivity.rootLayout);
    private EspTextView firstItemTextView = EspTextView.byText("item: 0");

    @Test
    public void testSwipe() {
        firstItemTextView.assertIsDisplayedOnScreen();

        espPage.swipeUp();
        firstItemTextView.assertNotExist();

        espPage.swipeDown();
        espPage.swipeDown();
        firstItemTextView.assertIsDisplayedOnScreen();
    }

    @Test
    public void testAssertIsHiddenFailureWhenOnlyPartialHidden() {
        scrollListPixelDistance(100);
        firstItemTextView.assertIsHidden();

        // value is optimized for circle ci emulators, may fail on different emulators
        scrollListPixelDistance(-70);
        firstItemTextView.assertIsPartiallyDisplayedOnly();
        firstItemTextView.assertIsPartiallyDisplayedOnScreen();

        exception.expect(AssertionFailedError.class);
        firstItemTextView.assertIsHidden();
    }

    @Test
    public void testAssertIsDisplayedOnScreenFailureWhenOnlyPartialHidden() {
        scrollListPixelDistance(100);

        exception.expect(AssertionFailedError.class);
        firstItemTextView.assertIsDisplayedOnScreen();
    }

    private void scrollListPixelDistance(final int distance) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                getActivity().findViewById(LongListActivity.listViewId).scrollBy(0, distance);
            }
        });
    }

    /** Element extension */
    class MyEspListView extends EspListView {

        public MyEspListView(EspListView template) {
            super(template);
        }

        @Override
        public MyEspAdapterViewItem itemByVisibleIndex(int index) {
            return new MyEspAdapterViewItem(super.itemByVisibleIndex(index));
        }
    }

    /** Element extension */
    class MyEspAdapterViewItem extends EspAdapterViewItem {

        public MyEspAdapterViewItem(EspAdapterViewItem template) {
            super(template);
        }

        public EspTextView textView() {
            return new EspTextView(baseMatcherForItemChild(withId(LongListActivity.itemTextViewId)));
        }
    }
}