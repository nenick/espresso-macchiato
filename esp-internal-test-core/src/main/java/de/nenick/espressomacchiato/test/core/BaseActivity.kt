package de.nenick.espressomacchiato.test.core

import android.app.Activity
import android.os.Bundle

/** Simple activity with just a root layout  */
class BaseActivity : Activity() {

    companion object {
        var rootLayout: Int = R.id.layout_activity_base
    }

    /** Simple create method  */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}