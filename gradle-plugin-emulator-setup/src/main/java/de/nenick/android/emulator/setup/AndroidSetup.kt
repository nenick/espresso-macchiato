package de.nenick.android.emulator.setup

import com.android.sdklib.AndroidVersion
import java.lang.IllegalStateException
import kotlin.reflect.KClass

sealed class AndroidSetup {

    protected abstract fun androidApi(): Int
    protected abstract fun systemImageAbi(): String
    fun systemImage() = "system-images;android-${androidApi()};google_apis;${systemImageAbi()}"

    abstract fun createAvdAdditionalArgs(): Array<out String>

    abstract fun avdSetting(): Array<String>
    open fun androidAppDataPath(): String { throw IllegalStateException("For android api ${androidApi()} this wasn't expected.") }
    open fun shouldRemountAsRoot() = false

    companion object {
        fun search(androidVersion: AndroidVersion): AndroidSetup {
            return search(androidVersion.apiLevel)
        }

        fun search(androidVersion: Int): AndroidSetup {
            return collectAllSetups().find { it.androidApi() == androidVersion }
                    ?: throw IllegalStateException("No setup found for android api $androidVersion")
        }

        private fun collectAllSetups() = AndroidSetup::class.sealedSubclasses
                .flatMap { collectRecursive(it) }
                .mapNotNull { it.objectInstance }

        private fun collectRecursive(current: KClass<out AndroidSetup>): List<KClass<out AndroidSetup>> {
            // Generates a plain list of all sub ... sub classes.
            return if (current.sealedSubclasses.isEmpty()) {
                listOf(current)
            } else {
                current.sealedSubclasses.flatMap {
                    collectRecursive(it)
                }
            }
        }
    }
}

// TODO When kotlin 1.5 is bundled with gradle, then sealed subclasses can be placed into own files.
//      Yet, it isn't possible to activate 1.5 features through kotlinOptions.languageVersion
//      for gradle plugin modules. For default java/android modules it's possible.
//      Gradle 7.X still use kotlin 1.4.xx we have to wait for gradle 8?

sealed class DefaultAndroidSetup : AndroidSetup() {
    override fun avdSetting() = arrayOf(
            // First we disable all what could be disabled for more performance.
            "s/=yes/=no/g",

            // GPU should still be enabled for performance.
            "s/hw.gpu.enabled=.*/hw.gpu.enabled=yes/g",

            // Avoid showing soft keyboard for more performance and less flakiness.
            "s/hw.keyboard=.*/hw.keyboard=yes/g",

            // Sdcard is useful for to provide additional test_data.
            // So "content query --uri content://media/external/file" will return something useful.
            "s/hw.sdCard=.*/hw.sdCard=yes/g"

            // For tests it should be possible to run completely offline. Otherwise enable it again.
            // updateConfigIni(avdName, "s/hw.gsmModem=.*/hw.gsmModem=yes/g")
    )
}

sealed class DefaultPreAndroid20Setup : DefaultAndroidSetup() {

    // There all haven't x86_64 system image.
    override fun systemImageAbi() = "x86"

    override fun createAvdAdditionalArgs() = arrayOf(
            // Seems like emulator for early android versions need sdcard setting at creation time
            // instead of reading it from config.ini file. Starting with android api 30 this
            // argument would block downloading test_data because it start searching on a path
            // where test_data content never appears.
            "--sdcard", "512M"
    )
}

object Android16 : DefaultPreAndroid20Setup() {
    override fun androidApi() = 16
    @Suppress("SdCardPath")
    // Never appears until any app creates some content.
    override fun androidAppDataPath() = "/mnt/sdcard/Android"
}

object Android18 : DefaultPreAndroid20Setup() {
    override fun androidApi() = 18
    // Never appears until any app creates some content.
    override fun androidAppDataPath() = "/storage/sdcard/Android"

// SecurityException: Calling from not trusted UID!

    // TODO try take screenshot with adb after crash
    // adb shell screencap -p /sdcard/screencap.png
    // adb pull /sdcard/screencap.png
    // exec-out does not exist on pre android api 20, nothing new ..

    // https://stackoverflow.com/questions/13578416/read-binary-stdout-data-from-adb-shell
    // adb shell screencap -p | sed 's|\r$||' > screenshot.png

    // screencap, there is another command screenshot, I don't know why screenshot was removed from Android 5.0, but it's avaiable below Android 4.4

//
// https://www.google.com/search?q=IllegalStateException%3A+UiAutomation+not+connected!&oq=IllegalStateException%3A+UiAutomation+not+connected!&aqs=chrome..69i57j69i58.763j0j4&sourceid=chrome&ie=UTF-8
// https://stackoverflow.com/questions/47854705/google-fabric-uiautomation-not-connected
//
// https://stackoverflow.com/questions/47498262/securityexception-calling-from-not-trusted-uid
//
//    I/TestRunner( 2824): run finished: 1 tests, 0 failed, 0 ignored
//    I/MonitoringInstr( 2824): waitForActivitiesToComplete() took: 0ms
//    W/dalvikvm( 2824): threadid=12: thread exiting with uncaught exception (group=0xacf84678)
//    D/dalvikvm( 2824): GC_CONCURRENT freed 352K, 16% free 4310K/5112K, paused 0ms+0ms, total 3ms
//    E/AndroidRuntime( 2824): FATAL EXCEPTION: Instr: androidx.test.runner.AndroidJUnitRunner
//    E/AndroidRuntime( 2824): java.lang.SecurityException: Calling from not trusted UID!
//    E/AndroidRuntime( 2824): 	at android.os.Parcel.readException(Parcel.java:1431)
//    E/AndroidRuntime( 2824): 	at android.os.Parcel.readException(Parcel.java:1385)
//    E/AndroidRuntime( 2824): 	at android.app.ActivityManagerProxy.finishInstrumentation(ActivityManagerNative.java:2954)
//    E/AndroidRuntime( 2824): 	at android.app.ActivityThread.finishInstrumentation(ActivityThread.java:4466)
//    E/AndroidRuntime( 2824): 	at android.app.Instrumentation.finish(Instrumentation.java:196)
//    E/AndroidRuntime( 2824): 	at androidx.test.runner.MonitoringInstrumentation.finish(MonitoringInstrumentation.java:367)
//    E/AndroidRuntime( 2824): 	at androidx.test.runner.AndroidJUnitRunner.finish(AndroidJUnitRunner.java:415)
//    E/AndroidRuntime( 2824): 	at androidx.test.runner.AndroidJUnitRunner.onStart(AndroidJUnitRunner.java:404)
//    E/AndroidRuntime( 2824): 	at android.app.Instrumentation$InstrumentationThread.run(Instrumentation.java:1701)
//    W/ActivityManager( 1445): Error in app de.nenick.espressomacchiato.android.material.test running instrumentation ComponentInfo{de.nenick.espressomacchiato.android.material.test/androidx.test.runner.AndroidJUnitRunner}:
//    W/ActivityManager( 1445):   java.lang.SecurityException
//    W/ActivityManager( 1445):   java.lang.SecurityException: Calling from not trusted UID!
//    I/Process ( 2824): Sending signal. PID: 2824 SIG: 9
//    E/AndroidRuntime( 2824): Error reporting crash
//    E/AndroidRuntime( 2824): java.lang.SecurityException: Calling from not trusted UID!
//    E/AndroidRuntime( 2824): 	at android.os.Parcel.readException(Parcel.java:1431)
//    E/AndroidRuntime( 2824): 	at android.os.Parcel.readException(Parcel.java:1385)
//    E/AndroidRuntime( 2824): 	at android.app.ActivityManagerProxy.handleApplicationCrash(ActivityManagerNative.java:3410)
//    E/AndroidRuntime( 2824): 	at com.android.internal.os.RuntimeInit$UncaughtHandler.uncaughtException(RuntimeInit.java:76)
//    E/AndroidRuntime( 2824): 	at androidx.appcompat.app.AppCompatDelegateImpl$1.uncaughtException(AppCompatDelegateImpl.java:177)
//    E/AndroidRuntime( 2824): 	at java.lang.ThreadGroup.uncaughtException(ThreadGroup.java:693)
//    E/AndroidRuntime( 2824): 	at java.lang.ThreadGroup.uncaughtException(ThreadGroup.java:690)
//    W/BinderNative( 1445): Uncaught exception from death notification
//    W/BinderNative( 1445): java.lang.SecurityException: Calling from not trusted UID!
//    W/BinderNative( 1445): 	at android.os.Parcel.readException(Parcel.java:1431)
//    W/BinderNative( 1445): 	at android.os.Parcel.readException(Parcel.java:1385)
//    W/BinderNative( 1445): 	at android.app.IUiAutomationConnection$Stub$Proxy.shutdown(IUiAutomationConnection.java:243)
//    W/BinderNative( 1445): 	at com.android.server.am.ActivityManagerService.finishInstrumentationLocked(ActivityManagerService.java:12504)
//    W/BinderNative( 1445): 	at com.android.server.am.ActivityManagerService.handleAppDiedLocked(ActivityManagerService.java:3024)
//    W/BinderNative( 1445): 	at com.android.server.am.ActivityManagerService.appDiedLocked(ActivityManagerService.java:3085)
//    W/BinderNative( 1445): 	at com.android.server.am.ActivityManagerService$AppDeathRecipient.binderDied(ActivityManagerService.java:897)
//    W/BinderNative( 1445): 	at android.os.BinderProxy.sendDeathNotice(Binder.java:470)
//    W/BinderNative( 1445): 	at dalvik.system.NativeStart.run(Native Method)
//    I/ActivityManager( 1445): Process de.nenick.espressomacchiato.android.material.test (pid 2824) has died.
//    W/ActivityManager( 1445): Crash of app de.nenick.espressomacchiato.android.material.test running instrumentation ComponentInfo{de.nenick.espressomacchiato.android.material.test/androidx.test.runner.AndroidJUnitRunner}
//    D/AndroidRuntime( 2813): Shutting down VM
}

object Android19 : DefaultPreAndroid20Setup() {
    override fun androidApi() = 19
}

object Android20 : AndroidSetup() {
    private val error = UnsupportedOperationException("Google doesn't provide x86 image for android ${androidApi()}")
    override fun androidApi() = 20
    override fun systemImageAbi() = throw error
    override fun createAvdAdditionalArgs() = throw error
    override fun avdSetting() = throw error
}

sealed class DefaultPostAndroid20Setup : DefaultAndroidSetup() {
    override fun systemImageAbi() = "x86_64"
    override fun createAvdAdditionalArgs() = emptyArray<String>()
}

object Android24 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 24
}

object Android29 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 29
}

object Android30 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 30
    // Would appear but takes too much time, so we shorten it a bit.
    override fun androidAppDataPath() = "/storage/emulated/0/Android"
    // Otherwise gradle fails to pull test_data directory.
    override fun shouldRemountAsRoot() = true
}