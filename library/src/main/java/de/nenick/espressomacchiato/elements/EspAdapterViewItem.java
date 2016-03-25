package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.DataInteraction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

public class EspAdapterViewItem extends EspView {

    private String dataSourceField;
    private String itemText;

    @Deprecated
    public static EspView byId(int resourceId) {
        throw new UnsupportedOperationException();
    }

    public static EspAdapterViewItem byText(String text, String dataSourceField) {
        return new EspAdapterViewItem(text, dataSourceField);
    }

    public EspAdapterViewItem(Matcher<View> baseMatcher) {
        super(baseMatcher);
    }

    public EspAdapterViewItem(String itemText, String dataSourceField) {
        this(withText(itemText));
        this.itemText = itemText;
        this.dataSourceField = dataSourceField;
    }

    public void scrollTo() {
        onRow(itemText, dataSourceField).check(matches(isCompletelyDisplayed()));
    }

    private static DataInteraction onRow(String text, String dataSourceField) {
        return onData(hasEntry(equalTo(dataSourceField), is(text)));
    }
}
