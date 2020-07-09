package de.nenick.espressomacchiato.elements.support;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.actions.support.AppBarLayoutAction;
import de.nenick.espressomacchiato.assertions.support.AppBarLayoutAssertion;
import de.nenick.espressomacchiato.elements.EspView;
import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;

/**
 * Action and assertions for AppBarLayouts.
 *
 * Support for {@link CollapsingToolbarLayout}
 *
 * @since Espresso Macchiato 0.6
 */
public class EspAppBarLayout extends EspView {

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.1
     */
    public static EspAppBarLayout byId(int resourceId) {
        return new EspAppBarLayout(resourceId);
    }

    /**
     * Create an allOf matcher builder for this element.
     *
     * @return New allOf matcher builder.
     *
     * @since Espresso Macchiato 0.4
     */
    public static EspAllOfBuilder<? extends EspAppBarLayout> byAll() {
        return new EspAllOfBuilder<EspAppBarLayout>() {
        };
    }

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @since Espresso Macchiato 0.6
     */
    public EspAppBarLayout(int resourceId) {
        super(resourceId);
    }

    /**
     * Create new element instance with custom base matcher.
     *
     * @param base Matcher for this element.
     *
     * @since Espresso Macchiato 0.6
     */
    public EspAppBarLayout(Matcher<View> base) {
        super(base);
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.6
     */
    public EspAppBarLayout(EspView template) {
        super(template);
    }

    /**
     * Collapse the app bar layout programmatically.
     *
     * @since Espresso Macchiato 0.6
     */
    public void collapse() {
        findView().perform(AppBarLayoutAction.collapse());
    }

    /**
     * Expand the app bar layout programmatically.
     *
     * @since Espresso Macchiato 0.6
     */
    public void expand() {
        findView().perform(AppBarLayoutAction.expand());
    }

    /**
     * Check if the app bar layout is full expanded.
     *
     * @since Espresso Macchiato 0.6
     */
    public void assertIsExpanded() {
        findView().check(AppBarLayoutAssertion.assertIsExpanded());
    }

    /**
     * Check if the app bar layout is full expanded.
     *
     * @since Espresso Macchiato 0.6
     */
    public void assertIsCollapsed() {
        findView().check(AppBarLayoutAssertion.assertIsCollapsed());
    }
}
