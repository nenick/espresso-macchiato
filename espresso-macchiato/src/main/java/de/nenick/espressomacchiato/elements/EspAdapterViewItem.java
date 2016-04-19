package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.DataInteraction;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

public class EspAdapterViewItem extends EspView {

    private String property;
    private String itemText;

    @Deprecated
    public static EspView byId(int resourceId) {
        throw new UnsupportedOperationException();
    }

    public static EspAdapterViewItem byText(String text, String property) {
        return new EspAdapterViewItem(text, property);
    }

    public EspAdapterViewItem(String itemText, String dataSourceField) {
        super(withText(itemText));
        this.itemText = itemText;
        this.property = dataSourceField;
    }

    public void scrollTo() {
        onRow(itemText, property).check(matches(isCompletelyDisplayed()));
    }

    protected DataInteraction onRow(Object value, String property) {
        return onData(hasEntry(equalTo(property), is(value)));
    }
}
