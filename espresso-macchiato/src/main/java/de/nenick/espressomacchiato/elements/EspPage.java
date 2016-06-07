package de.nenick.espressomacchiato.elements;

import android.view.View;

import org.hamcrest.Matcher;

/**
 * Base class for new page objects.
 *
 * A page could be an activity, fragment or just a partial view.
 * Recommend is to give your root layout a unique id to safely check that a specific page is currently shown.
 *
 * EspPage is not necessary for writing page objects.
 * But it provides some actions and assertions you could do on pages.
 *
 * <span class="simpleTagLabel">Example usage:</span><br>
 *
 * Create subclass with some page elements.
 *
 * <!-- <pre> -->
 * ```java
 * public class MyPage extends EspPage {
 *
 *     public MyPage() {
 *         super(R.id.activity_root_my);
 *     }
 *
 *     public EspTextView someTextView() {
 *         return EspTextView.byId(R.id.textView);
 *     }
 *
 *     public EspTextView someButton() {
 *         return EspButton.byId(R.id.button);
 *     }
 * }
 * ```
 * <!-- </pre> -->
 *
 * Then use it in your tests.
 *
 * <!-- <pre> -->
 * ```java
 * public class MyPageTest {
 *
 *     MyPage myPage = new MyPage();
 *
 *     public void testSomething() {
 *         myPage.someTextView().assertTextIs("");
 *         myPage.someButton().click();
 *         myPage.someTextView().assertTextIs("Clicked");
 *     }
 * }
 * ```
 * <!-- </pre> -->
 *
 * @since Espresso Macchiato 0.1
 */
public class EspPage extends EspView {

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this page base layout.
     *
     * @return New instance for assertions and actions.
     *
     * @since Espresso Macchiato 0.1
     */
    public static EspPage byId(int resourceId) {
        return new EspPage(resourceId);
    }

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @since Espresso Macchiato 0.1
     */
    public EspPage(int resourceId) {
        super(resourceId);
    }

    /**
     * Create new element instance with custom base matcher.
     *
     * @param base Matcher for this element.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspPage(Matcher<View> base) {
        super(base);
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspPage(EspPage template) {
        super(template);
    }
}
