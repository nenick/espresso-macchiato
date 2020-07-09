package de.nenick.espressomacchiato.test.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.nenick.espressomacchiato.test.R;

/**
 * Activity with RecyclerView and 100 entries.
 */
public class LongRecyclerActivity extends Activity {

    public static final String dataSourceTextColumn = "ROW_TEXT";
    public static final int recyclerViewId = R.id.list;
    public static final int selectedRotTextViewId = R.id.selection_row_value;
    public static final int recyclerViewItemCount = 100;
    public static final long lastRecyclerItemId = 99;
    public static final int itemTextViewId = R.id.rowContentTextView;
    public static final String lastRecyclerItemText = "item: " + lastRecyclerItemId;

    protected static final String ROW_ENABLED = "ROW_ENABLED";
    protected static final String ITEM_TEXT_FORMAT = "item: %d";

    private List<Map<String, Object>> data = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_recycler);
        populateData();

        RecyclerView listView = (RecyclerView) findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter adapter = new LongListAdapter();

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
        for (int i = 0; i < recyclerViewItemCount; i++) {
            data.add(makeItem(i));
        }
    }

    /** View holder for list entries */
    private class VHolder extends RecyclerView.ViewHolder {

        private TextView content;
        private ToggleButton toggle;

        public VHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.rowContentTextView);
            toggle = (ToggleButton) itemView.findViewById(R.id.rowToggleButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggle.toggle();
                }
            });
        }

        public void bind(Map<String, Object> item) {
            content.setText((String) item.get(dataSourceTextColumn));
            toggle.setChecked((Boolean) item.get(ROW_ENABLED));
        }
    }

    /** Adapter with some dummy data */
    private class LongListAdapter extends RecyclerView.Adapter<VHolder> {

        @Override
        public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new VHolder(view);
        }

        @Override
        public void onBindViewHolder(VHolder holder, int position) {
            holder.bind(LongRecyclerActivity.this.data.get(position));
        }

        @Override
        public int getItemCount() {
            return LongRecyclerActivity.this.data.size();
        }
    }
}
