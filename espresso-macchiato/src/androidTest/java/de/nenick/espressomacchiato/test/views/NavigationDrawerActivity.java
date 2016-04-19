package de.nenick.espressomacchiato.test.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.nenick.espressomacchiato.test.R;

public class NavigationDrawerActivity extends AppCompatActivity {

    public static int rootLayout = R.id.layout_activity_navigation_drawer;
    public static int drawerLayout = R.id.drawer_layout;
    public static int drawerNavigationView = R.id.drawer_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
    }
}
