package de.nenick.espressomacchiato.androidx.test.tools

import android.app.Activity
import android.os.Bundle
import de.nenick.espressomacchiato.androidx.drawer.test.R

class DrawerActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
    }
}