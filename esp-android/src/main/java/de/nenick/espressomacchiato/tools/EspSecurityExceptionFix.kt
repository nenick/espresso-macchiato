package de.nenick.espressomacchiato.tools

import android.util.Log

object EspSecurityExceptionFix {

    private lateinit var originExceptionHandler: Thread.UncaughtExceptionHandler
    private val ignoringSecurityExceptionHandler = Thread.UncaughtExceptionHandler { t, e ->
        if (isNotTrustedUidException(e)) {
            // Dirty hack to avoid random Instrumentation run crashes.
            //
            //     4.3.1 failed: Instrumentation run failed due to 'java.lang.SecurityException'
            //     4.3.1 failed: Instrumentation run failed due to 'Process crashed.'
            //
            // java.lang.SecurityException: Calling from not trusted UID!
            //     at android.os.Parcel.readException(Parcel.java:1431)
            //     at android.os.Parcel.readException(Parcel.java:1385)
            //     at android.app.ActivityManagerProxy.finishInstrumentation(ActivityManagerNative.java:2954)
            //     at android.app.ActivityThread.finishInstrumentation(ActivityThread.java:4466)
            //     at android.app.Instrumentation.finish(Instrumentation.java:196)
            //     at androidx.test.runner.MonitoringInstrumentation.finish(MonitoringInstrumentation.java:367)
            //     at androidx.test.runner.AndroidJUnitRunner.finish(AndroidJUnitRunner.java:415)
            //     at androidx.test.runner.AndroidJUnitRunner.onStart(AndroidJUnitRunner.java:404)
            //     at android.app.Instrumentation$InstrumentationThread.run(Instrumentation.java:1701)
            //
            // Happens at the last cleanup action. Just global ignoring it does result into additional crash log.
            //
            //     system_process W/ActivityManager: Error in app ...
            //     system_process W/ActivityManager: Crash of app ...
            //
            // Didn't found any bad side effects by just ignoring it, yet. Other option would be to write a custom
            // AndroidJUnitRunner and catch the SecurityException by overriding the finish(Int, Bundle?) method.
            Log.w("AndroidJUnitRunner", "Ignored random SecurityException when instrumentation finishes.", e)
        } else {
            originExceptionHandler.uncaughtException(t, e)
        }
    }

    private fun isNotTrustedUidException(e: Throwable) = e is SecurityException &&
        e.message == "Calling from not trusted UID!" &&
        e.stackTraceToString().contains("androidx.test.runner.AndroidJUnitRunner.finish")

    fun apply() {
        val currentExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        if (currentExceptionHandler != ignoringSecurityExceptionHandler) {
            originExceptionHandler = currentExceptionHandler!!
            Thread.setDefaultUncaughtExceptionHandler(ignoringSecurityExceptionHandler)
        }
    }
}