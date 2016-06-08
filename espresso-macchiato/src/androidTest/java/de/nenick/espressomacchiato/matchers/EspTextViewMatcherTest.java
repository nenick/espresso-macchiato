package de.nenick.espressomacchiato.matchers;

import android.support.test.InstrumentationRegistry;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.junit.Assert.assertFalse;

/** Basic test */
public class EspTextViewMatcherTest extends EspressoTestCase<BaseActivity> {

    public static final String BLACK = "Black";
    public static final String WHITE = "White";

    @Test
    public void testForCoverage() {
        new EspTextViewMatcher();
    }

    @Test
    public void testOnlyMatchTextView() {
        Matcher<View> colorMatcher = EspTextViewMatcher.withTextColor(0xff000000);
        View nonTextView = new View(InstrumentationRegistry.getContext());
        assertFalse(colorMatcher.matches(nonTextView));
    }

    @Test
    public void testMatchColor() {
        TextView textView = new TextView(activityTestRule.getActivity());
        textView.setText(BLACK);
        textView.setTextColor(0xff000000);
        addViewToLayout(textView, BaseActivity.rootLayout);

        TextView textView2 = new TextView(activityTestRule.getActivity());
        textView2.setText(WHITE);
        textView2.setTextColor(0xffffffff);
        addViewToLayout(textView2, BaseActivity.rootLayout);

        new EspTextView(EspTextViewMatcher.withTextColor(0xffffffff)).assertTextIs(WHITE);
    }
}