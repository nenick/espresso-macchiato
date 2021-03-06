package de.nenick.espressomacchiato.testtools

import android.app.Activity
import android.os.Bundle
import androidx.annotation.Nullable
import de.nenick.espressomacchiato.widget.EspTextView
import de.nenick.espressomacchiato.espresso.view.ClickActions
import de.nenick.espressomacchiato.test.R

class MyLabelB(id:Int) : EspTextView(id), ClickActions {}

object InoutPage {

    val labelA = EspTextView(android.R.id.message)
    //val labelB = object : EspTextView(android.R.id.message), ClickActions {}
            //    val labelB = MyLabelB(android.R.id.message)

    val blub = MyLabelB(android.R.id.message)
}

/** Simple activity with just a root layout  */
class BaseActivity : Activity() {
    /** Simple create method  */
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)





//        val view = espTextView()
//
//        view.checkText("blub")
//        view.performClick()



    }

//    fun espTextView() = object : EspTextView(android.R.id.message), ClickActions {}

    companion object {
        var rootLayout: Int = R.id.layout_activity_base
    }
}