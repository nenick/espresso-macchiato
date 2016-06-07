package de.nenick.espressomacchiato.elements;

/**
 * Actions and assertions for a ListView.
 */
public class EspListView extends EspAdapterView {

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.3
     */
    public static EspListView byId(int resourceId) {
        return new EspListView(resourceId);
    }

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @since Espresso Macchiato 0.3
     */
    public EspListView(int resourceId) {
        super(resourceId);
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspListView(EspListView template) {
        super(template);
    }
}
