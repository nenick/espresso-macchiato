package de.nenick.android.emulator.setup

import com.android.sdklib.AndroidVersion
import kotlin.reflect.KClass

sealed class AndroidSetup {

    protected abstract fun androidApi(): Int

    open fun externalDirectory(): String {
        error("For android api ${androidApi()} this wasn't expected.")
    }

    open fun shouldRemountAsRoot() = false
    open fun shortCutContentMediaAndroid() = false

    // $ANDROID_HOME/platform-tools/adb shell "su root pm list packages"
    open fun disablePackages() = emptyList<String>()

    // https://developer.android.com/reference/android/provider/Settings
    fun adjustSettings() = listOf(
        // Disable animations for more speed and less flakiness on emulators.
        "global window_animation_scale 0",
        "global transition_animation_scale 0",
        "global animator_duration_scale 0",
        // Disable keyguard completely to avoid issue logs.
        "secure lockscreen.disabled 1",
        // Disable all sound. Otherwise logcat get polluted by audio_hw_generic: Hardware backing HAL too slow
        "system sound_effects_enabled 0",
        // Stop notifications from disturbing view interactions from espresso.
        "global heads_up_notifications_enabled 0"
    )

    companion object {
        fun search(androidVersion: AndroidVersion): AndroidSetup {
            return search(androidVersion.apiLevel)
        }

        private fun search(androidVersion: Int): AndroidSetup {
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

    override fun disablePackages() = listOf(
        // Crashed sometimes in the middle of the test run.
        // android 18
        "com.android.com.android.email",
        // android 19
        "android.process.acore",
        // android 22 - CalendarStorage is still crashing, how to avoid it??
        "com.android.providers.calendar",
        "com.google.android.calendar",
        // android 24
        "com.google.android.partnersetup",
        // android 30
        "com.google.android.apps.maps",
        "com.google.android.apps.wellbeing",

        // Would complain about disabled GMS services.
        "com.google.android.apps.docs", // aka Google Drive App,
        "com.google.android.im",
        "com.google.android.dialer",
        "com.android.dialer",
        "com.google.android.videos",
        "com.google.android.apps.wallpaper",

        // Would be too easy, it would make common test scenarios (e.g. use google maps) Impossible.
        // "com.google.android.gms", // aka Google Mobile Services

        // Reduce log spam.
        "com.google.android.apps.messaging",
        "com.android.phone", // Has no reducing effect ??
        "com.google.android.talk",
        "com.google.android.googlequicksearchbox",
        "com.google.android.configupdater",
        "com.android.bluetooth",
        "com.android.carrierconfig",
        "com.android.carrierdefaultapp",
        "com.android.providers.telephony",
        "com.google.android.youtube",
        "com.google.android.apps.youtube.music"
    )
}

sealed class DefaultPreAndroid20Setup : DefaultAndroidSetup() {

}

object Android16 : DefaultPreAndroid20Setup() {
    override fun androidApi() = 16

    // Never appears until any app creates some content.
    override fun shortCutContentMediaAndroid() = true
    override fun externalDirectory() = "/mnt/sdcard"
}

object Android17 : DefaultPreAndroid20Setup() {
    override fun androidApi() = 17

    // Never appears until any app creates some content.
    override fun shortCutContentMediaAndroid() = true
    override fun externalDirectory() = "/mnt/sdcard"
}

object Android18 : DefaultPreAndroid20Setup() {
    override fun androidApi() = 18

    // Never appears until any app creates some content.
    // But we can't immediately create it, or gradle get issues to find it.
    override fun shortCutContentMediaAndroid() = false
    override fun externalDirectory() = "/storage/sdcard"
}

object Android19 : DefaultPreAndroid20Setup() {
    override fun androidApi() = 19
}

object Android20 : AndroidSetup() {
    private val error = UnsupportedOperationException("Google doesn't provide x86 image for android ${androidApi()}")
    override fun androidApi() = 20
}

sealed class DefaultPostAndroid20Setup : DefaultAndroidSetup() {

}

object Android21 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 21
}

object Android22 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 22
}

object Android23 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 23
}

object Android24 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 24
}

object Android25 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 25
}

object Android26 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 26
}

object Android27 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 27
}

object Android28 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 28
}

object Android29 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 29

    // Would appear but takes too much time, so we shorten it a bit.
    override fun shortCutContentMediaAndroid() = true
    override fun externalDirectory() = "/storage/emulated/0"
}

object Android30 : DefaultPostAndroid20Setup() {
    override fun androidApi() = 30

    // Would appear but takes too much time, so we shorten it a bit.
    override fun shortCutContentMediaAndroid() = true
    override fun externalDirectory() = "/storage/emulated/0"

    // Otherwise gradle fails to pull test_data directory.
    override fun shouldRemountAsRoot() = true
}