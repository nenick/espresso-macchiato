package de.nenick.espressomacchiato.internals

import android.view.View
import android.widget.CompoundButton
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

class ClickWithRetryAction : ViewAction {

    class ClickInterceptor(
        private val view: View,
        private val originClickListener: Any,
        private val clickListener: () -> Unit
    ) : View.OnClickListener {
        override fun onClick(v: View?) {
            clickListener()
            originClickListener
                .javaClass.declaredMethods.find { it.name == "onClick" }!!
                .invoke(originClickListener, view)
        }
    }

    private val startTime by lazy { System.currentTimeMillis() }

    override fun getConstraints(): Matcher<View> = ViewMatchers.isDisplayed()

    override fun getDescription() = "click with retry"

    override fun perform(uiController: UiController, view: View) {
        if (hasClickListener(view)) {
            performRetryClick(uiController, view)
        } else {
            performOriginClick(uiController, view)
        }
    }

    private fun performRetryClick(uiController: UiController, view: View) {
        var clickPerformed = false
        interceptOnClickListener(view) {
            clickPerformed = true
        }

        try {
            performOriginClick(uiController, view)
        } catch (_: PerformException) {
            // Sometimes a click is failing because of ...
            // androidx.test.espresso.PerformException:
            //     Error performing 'single click - At Coordinates: .. and precision: ..' on view '..'
            // Ignore yet and let us retry.
        }

        while (!clickPerformed) {
            if (isTimeout()) {
                throw InterruptedException("Click wasn't recognize.")
            }
            Thread.sleep(delayNextAttemptMilliseconds)
            performOriginClick(uiController, view)
        }
    }

    // Usually CompoundButton (e.g. CheckBox) react self to click events instead to a click listener.
    // When necessary than it could be improved to check if view has really a click lister for interception.
    private fun hasClickListener(view: View) = view !is CompoundButton

    private fun performOriginClick(uiController: UiController, view: View) {
        ViewActions.click().perform(uiController, view)
    }

    private fun interceptOnClickListener(view: View, clickListener: () -> Unit) {
        val listenerInfoField = View::class.java.getDeclaredField("mListenerInfo").apply { isAccessible = true }
        val listenerInfo = listenerInfoField.get(view)
        checkNotNull(listenerInfo) { "No click listener found for ${view.javaClass.name}." }
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