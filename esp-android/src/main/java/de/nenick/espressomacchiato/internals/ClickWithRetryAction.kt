package de.nenick.espressomacchiato.internals

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

class ClickWithRetryAction : ViewAction {

    class ClickInterceptor(
        private val view: View,
        private val originClickListener: Any,
        private val clickListener: () -> Unit
    ) : View.OnClickListener {
        override fun onClick(v: View?) {
            originClickListener.javaClass.declaredMethods[0].invoke(originClickListener, view)
            clickListener()
        }
    }

    private val startTime by lazy { System.currentTimeMillis() }

    override fun getConstraints(): Matcher<View> = ViewMatchers.isDisplayed()

    override fun getDescription() = "click with retry"

    override fun perform(uiController: UiController, view: View) {
        var clickPerformed = false

        interceptOnClickListener(view) {
            clickPerformed = true
        }

        while (!clickPerformed) {
            if (isTimeout()) {
                throw InterruptedException("Click wasn't recognize.")
            }

            view.performClick()
            Thread.sleep(delayNextAttemptMilliseconds)
        }
    }

    private fun interceptOnClickListener(view: View, clickListener: () -> Unit) {
        val listenerInfoField = View::class.java.getDeclaredField("mListenerInfo").apply { isAccessible = true }
        val listenerInfo = listenerInfoField.get(view)
        val onClickListenerField = listenerInfo.javaClass.getDeclaredField("mOnClickListener")
        val originClickListener = onClickListenerField.get(listenerInfo)!!
        val clickInterceptor = ClickInterceptor(view, originClickListener, clickListener)
        onClickListenerField.set(listenerInfo, clickInterceptor)
    }

    private fun isTimeout() = System.currentTimeMillis() - startTime > timeoutMilliseconds

    companion object {
        private const val delayNextAttemptMilliseconds = 20L
        private const val timeoutMilliseconds = 2 * 1000L
    }
}