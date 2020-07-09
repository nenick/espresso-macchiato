package de.nenick.espressomacchiato.matchers;

import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import de.nenick.espressomacchiato.elements.EspView;

/**
 * Fluent builder fo allOf matcher.
 *
 * @param <ElementT> View element type which will be used to perform actions and assertions on match.
 * @since Espresso Macchiato 0.4
 */
public abstract class EspAllOfBuilder<ElementT extends EspView> {

    protected ArrayList<Matcher<? super View>> matcher = Lists.newArrayList();

    /**
     * Add matcher for element id.
     *
     * @param resourceId expected id
     * @return The current builder.
     * @since Espresso Macchiato 0.4
     */
    public EspAllOfBuilder<ElementT> withId(int resourceId) {
        matcher.add(ViewMatchers.withId(resourceId));
        return this;
    }

    /**
     * Add matcher that element is displayed.
     *
     * @return The current builder.
     * @since Espresso Macchiato 0.4
     */
    public EspAllOfBuilder<ElementT> withIsDisplayed() {
        matcher.add(ViewMatchers.isDisplayed());
        return this;
    }

    /**
     * Add matcher that element must have an expected parent with id.
     *
     * @param resourceId expected parent id
     * @return The current builder.
     * @since Espresso Macchiato 0.4
     */
    public EspAllOfBuilder<ElementT> withParentInHierarchy(int resourceId) {
        matcher.add(ViewMatchers.isDescendantOfA(ViewMatchers.withId(resourceId)));
        return this;
    }

    /**
     * Add matcher for text.
     *
     * @param text expected text
     * @return The current builder.
     * @since Espresso Macchiato 0.6
     */
    public EspAllOfBuilder<ElementT> withText(String text) {
        matcher.add(ViewMatchers.withText(text));
        return this;
    }

    /**
     * Add matcher for visibility.
     *
     * @param visible expected visibility
     * @return The current builder.
     * @since Espresso Macchiato 0.6
     */
    public EspAllOfBuilder<ElementT> withVisibility(ViewMatchers.Visibility visible) {
        matcher.add(ViewMatchers.withEffectiveVisibility(visible));
        return this;
    }

    /**
     * Create an view element instance with the given matcher.
     *
     * @return New instance of the defined view element.
     * @since Espresso Macchiato 0.4
     */
    public ElementT build() {
        try {
            Matcher<View> allOfMatcher = Matchers.allOf(matcher);
            return getGenericClass().getConstructor(Matcher.class).newInstance(allOfMatcher);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Class<ElementT> getGenericClass() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            //noinspection unchecked
            return ((Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        } else {
            throw new IllegalArgumentException("Please provide generic element");
        }
    }
}