package de.nenick.espressomacchiato.testbase;

import android.os.Build;

/**
 * Original code comes from https://github.com/square/spoon/blob/master/spoon-client/src/main/java/com/squareup/spoon/Spoon.java
 */
public class EspFindTestClassFunction {

    static final String TEST_CASE_CLASS_JUNIT_3 = "android.test.InstrumentationTestCase";
    static final String TEST_CASE_METHOD_JUNIT_3 = "runMethod";
    static final String TEST_CASE_CLASS_JUNIT_4 = "org.junit.runners.model.FrameworkMethod$1";
    static final String TEST_CASE_METHOD_JUNIT_4 = "runReflectiveCall";
    static final String TEST_CASE_CLASS_CUCUMBER_JVM = "cucumber.runtime.model.CucumberFeature";
    static final String TEST_CASE_METHOD_CUCUMBER_JVM = "run";

    /** Returns the test class element by looking at the method InstrumentationTestCase invokes. */
    public static StackTraceElement apply(StackTraceElement[] trace) {
        for (int i = trace.length - 1; i >= 0; i--) {
            StackTraceElement element = trace[i];
            if (TEST_CASE_CLASS_JUNIT_3.equals(element.getClassName()) //
                    && TEST_CASE_METHOD_JUNIT_3.equals(element.getMethodName())) {
                return extractStackElement(trace, i);
            }

            if (TEST_CASE_CLASS_JUNIT_4.equals(element.getClassName()) //
                    && TEST_CASE_METHOD_JUNIT_4.equals(element.getMethodName())) {
                return extractStackElement(trace, i);
            }
            if (TEST_CASE_CLASS_CUCUMBER_JVM.equals(element.getClassName()) //
                    && TEST_CASE_METHOD_CUCUMBER_JVM.equals(element.getMethodName())) {
                return extractStackElement(trace, i);
            }
        }

        throw new IllegalArgumentException("Could not find test class!");
    }

    private static StackTraceElement extractStackElement(StackTraceElement[] trace, int i) {
        //Stacktrace length changed in M
        int testClassTraceIndex = Build.VERSION.SDK_INT >= 23 ? (i - 2) : (i - 3);
        return trace[testClassTraceIndex];
    }
}
