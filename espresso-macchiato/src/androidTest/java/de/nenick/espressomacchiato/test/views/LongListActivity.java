package de.nenick.espressomacchiato.test.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.nenick.espressomacchiato.test.R;

/**
 * Activity with ListView and 100 entries.
 *
 * Copy of from https://github.com/googlesamples/android-testing/blob/master/ui/espresso/DataAdapterSample/app/src/main/java/com/example/android/testing/espresso/DataAdapterSample/LongListActivity.java
 */
public class LongListActivity extends Activity {

    public static final int rootLayout = R.id.layout_activity_list;
    public static final String dataSourceTextColumn = "ROW_TEXT";
    public static final int listViewId = R.id.list;
    public static final int itemTextViewId = R.id.rowContentTextView;
    public static final int selectedRotTextViewId = R.id.selection_row_value;
    public static final int listViewItemCount = 100;
    public static final long lastListItemId = 99;
    public static final String lastListItemText = "item: " + lastListItemId;

    protected static final String ROW_ENABLED = "ROW_ENABLED";
    protected static final String ITEM_TEXT_FORMAT = "item: %d";

    private List<Map<String, Object>> data = new ArrayList<>();

    private LayoutInflater layoutInflater;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_list);
        populateData();

        ListView listView = (ListView) findViewById(R.id.list);
        String[] from = new String[]{dataSourceTextColumn, ROW_ENABLED};
        int[] to = new int[]{R.id.rowContentTextView, R.id.rowToggleButton};
        layoutInflater = getLayoutInflater();

        // Create the adapter for the list.
        ListAdapter adapter = new LongListAdapter(from, to);

        // Send the data to the list.
        listView.setAdapter(adapter);
    }

    private static Map<String, Object> makeItem(int forRow) {
        Map<String, Object> dataRow = new HashMap<>();
        dataRow.put(dataSourceTextColumn, String.format(Locale.US, ITEM_TEXT_FORMAT, forRow));
        dataRow.put(ROW_ENABLED, forRow == 1);
        return dataRow;
    }

    private void populateData() {
        for (int i = 0; i < listViewItemCount; i++) {
            data.add(makeItem(i));
        }
    }

    /** Adapter with some dummy data */
    private class LongListAdapter extends SimpleAdapter {

        public LongListAdapter(String[] from, int[] to) {
            super(LongListActivity.this, LongListActivity.this.data, R.layout.list_item, from, to);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Inflate list items.
            View view;
            if (null == convertView) {
                view = layoutInflater.inflate(R.layout.list_item, null);
            } else {
                view = convertView;
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TextView) findViewById(R.id.selection_row_value)).setText(
                            String.valueOf(position));
                }
            });

            return super.getView(position, convertView, parent);
        }
    }
}
