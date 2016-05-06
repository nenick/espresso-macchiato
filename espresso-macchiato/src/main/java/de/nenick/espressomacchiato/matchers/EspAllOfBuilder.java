package de.nenick.espressomacchiato.matchers;

import android.support.test.espresso.core.deps.guava.collect.Lists;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import de.nenick.espressomacchiato.elements.EspView;

public abstract class EspAllOfBuilder<ElementT extends EspView> {

    protected ArrayList<Matcher<? super View>> matcher = Lists.newArrayList();

    public EspAllOfBuilder<ElementT> withId(int resourceId) {
        matcher.add(ViewMatchers.withId(resourceId));
        return this;
    }

    public EspAllOfBuilder<ElementT> withIsDisplayed() {
        matcher.add(ViewMatchers.isDisplayed());
        return this;
    }

    public EspAllOfBuilder<ElementT> withParentInHierarchy(int resourceId) {
        matcher.add(ViewMatchers.isDescendantOfA(ViewMatchers.withId(resourceId)));
        return this;
    }

    public ElementT build() {
        try {
            Matcher<View> allOfMatcher = Matchers.allOf(matcher);
            return getGenericClass().getConstructor(Matcher.class).newInstance(allOfMatcher);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    protected Class<ElementT> getGenericClass() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            //noinspection unchecked
            return ((Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        } else {
            throw new IllegalArgumentException("Please provide generic element");
        }
    }
}