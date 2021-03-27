package de.nenick.espressomacchiato.android.material.test.tools

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.nenick.espressomacchiato.android.material.test.R

class CollapsibleToolbarActivity : AppCompatActivity() {

    companion object {
        const val titleText = "Collapsible"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsible_toolbar)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.title = titleText
    }
}