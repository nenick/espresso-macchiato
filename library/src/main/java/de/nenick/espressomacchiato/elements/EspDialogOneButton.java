package de.nenick.espressomacchiato.elements;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspDialogOneButton extends EspDialog {
    private int titleResource;
    private int messageResource;
    private int confirmButtonResource;

    public EspDialogOneButton(int rootResource, int titleResource, int messageResource, int confirmButtonResource) {
        super(rootResource);
        this.titleResource = titleResource;
        this.messageResource = messageResource;
        this.confirmButtonResource = confirmButtonResource;
    }

    public EspTextView title() {
        return EspTextView.byId(titleResource);
    }

    public EspTextView message() {
        return EspTextView.byId(messageResource);
    }

    public void assertTitleTextIs(String expectedText) {
        onView(withId(titleResource)).check(matches(withText(expectedText)));
    }

    public void assertMessageTextIs(String expectedText) {
        onView(withId(messageResource)).check(matches(withText(expectedText)));
    }

    public EspButton confirmButton() {
        return EspButton.byId(confirmButtonResource);
    }
}
