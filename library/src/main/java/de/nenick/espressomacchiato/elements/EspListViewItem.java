package de.nenick.espressomacchiato.elements;

public class EspListViewItem extends EspAdapterViewItem {

    public static EspListViewItem byText(String text, String dataSourceField) {
        return new EspListViewItem(text, dataSourceField);
    }

    public EspListViewItem(String itemText, String dataSourceField) {
        super(itemText, dataSourceField);
    }
}
