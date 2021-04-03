package de.nenick.espressomacchiato.screenshot

import org.junit.Test
import org.junit.runner.Description
import org.junit.runners.model.Statement
import androidx.test.ext.junit.rules.activityScenarioRule
import de.nenick.espressomacchiato.screenshot.internal.AdditionalTestDataZipper
import de.nenick.espressomacchiato.test.core.BaseActivityTest
import de.nenick.espressomacchiato.test.core.BaseActivity
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Rule
import java.lang.Exception
import java.lang.IllegalStateException

class EspScreenshotRuleTest : BaseActivityTest<BaseActivity>() {

    @get:Rule
    override val activityScenarioRule = activityScenarioRule<BaseActivity>()

    private val testSuccessfulDescription = Description.createTestDescription("com.example.MySuccessTest", "isSuccessful")
    private val testSuccessful = object : Statement() {
        override fun evaluate() {
            // test does not fail
        }
    }

    private val testFailureDescription = Description.createTestDescription("com.example.MyFailureTest", "doesFail")
    private val testFailure = object : Statement() {
        override fun evaluate() {
            throw Exception("A failure occurred.")
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
        expectThrowing {
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

    private fun expectThrowing(block: () -> Unit) {
        try {
            block()
        } catch (e: Exception) {
            return
        }
        throw IllegalStateException("block haven't throw an exception, but was expected")
    }
}