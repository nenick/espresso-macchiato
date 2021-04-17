package de.nenick.espressomacchiato.screenshot

import android.os.Build
import android.util.Log
import org.junit.Test
import org.junit.runner.Description
import org.junit.runners.model.Statement
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import de.nenick.espressomacchiato.screenshot.internal.AdditionalTestDataZipper
import de.nenick.espressomacchiato.test.core.BaseActivityTest
import de.nenick.espressomacchiato.test.core.BaseActivity
import org.hamcrest.CoreMatchers
import org.junit.Assert
import java.lang.Exception
import java.lang.IllegalStateException

class EspScreenshotRuleTest : BaseActivityTest<BaseActivity>() {

    override val activityScenarioRule = activityScenarioRule<BaseActivity>()

    private val testSuccessfulDescription = Description.createTestDescription("${javaClass.name}SuccessSample", "isSuccessful")
    private val testSuccessful = object : Statement() {
        override fun evaluate() {
            // test does not fail
        }
    }

    private val testFailureReason = Exception("A failure occurred.")
    private val testFailureDescription = Description.createTestDescription("${javaClass.name}FailureSample", "doesFail")
    private val testFailure = object : Statement() {
        override fun evaluate() {
            throw testFailureReason
        }
    }

    @Test
    fun ignoreWhenSuccessful() {
        EspScreenshotRule()
                .apply(testSuccessful, testSuccessfulDescription)
                .evaluate()

        Assert.assertThat(
                AdditionalTestDataZipper.screenshotDirectory.list()!!.asList(),
                CoreMatchers.not(CoreMatchers.hasItem(testSuccessfulDescription.className)))
    }

    @Test
    fun takesScreenshotOnFailure() {
        // Start app view must be done before we can take a screenshot.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        }

        expectThrowing(testFailureReason) {
            EspScreenshotRule()
                    .apply(testFailure, testFailureDescription)
                    .evaluate()
        }

        Assert.assertThat(
                AdditionalTestDataZipper.screenshotDirectory.list()!!.asList(),
                CoreMatchers.hasItem(testFailureDescription.className))

        val classFolder = AdditionalTestDataZipper.screenshotDirectory.listFiles { path -> path.name == testFailureDescription.className }!!.first()
        Assert.assertThat(
                classFolder.list()!!.asList(),
                CoreMatchers.hasItem(testFailureDescription.methodName))

        val methodFolder = classFolder.listFiles { path -> path.name == testFailureDescription.methodName }!!.first()
        Assert.assertThat(
                methodFolder.list()!!.asList(),
                CoreMatchers.hasItem(CoreMatchers.containsString(" failure.png")))
    }

    private fun expectThrowing(expected: Exception, block: () -> Unit) {
        try {
            block()
        } catch (e: Exception) {
            if(e == expected) {
                return
            } else {
                throw e
            }
        }
        throw IllegalStateException("block haven't throw an exception, but was expected")
    }
}