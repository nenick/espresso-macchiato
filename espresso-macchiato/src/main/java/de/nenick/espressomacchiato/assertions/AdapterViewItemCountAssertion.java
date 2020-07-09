package de.nenick.espressomacchiato.assertions;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import android.view.View;
import android.widget.AdapterView;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AdapterViewItemCountAssertion implements ViewAssertion {
    private final int expectedCount;

    public AdapterViewItemCountAssertion(int expectedCount) {
        this.expectedCount = expectedCount;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        AdapterView adapterView = (AdapterView) view;
        assertThat(adapterView.getCount(), is(expectedCount));
    }
}
