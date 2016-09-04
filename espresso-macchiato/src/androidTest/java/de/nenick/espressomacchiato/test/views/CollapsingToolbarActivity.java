package de.nenick.espressomacchiato.test.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.nenick.espressomacchiato.test.R;

/** Simple activity with just a root layout */
public class CollapsingToolbarActivity extends AppCompatActivity {

    public static int rootLayout = R.id.layout_activity_collapsing_toolbar;
    public static int appBarLayoutId = R.id.app_bar_layout;
    public static int collapseLayoutId = R.id.collapsing_toolbar;

    /** Simple create method */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
