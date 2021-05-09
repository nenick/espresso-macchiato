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

    // $ANDROID_HOME/platform-tools/adb shell dumpsys activity services
    //https://forum.xda-developers.com/t/script-guide-reduce-google-play-services-battery-drain-8-3-0-0.3139032/page-13#post-66741288
    open fun disableServices() = emptyList<String>()

    // https://developer.android.com/reference/android/provider/Settings
    fun adjustSettings() = listOf(
        // Disable animations for more speed and less flakiness on emulators.
        "global window_animation_scale 0",
        "global transition_animation_scale 0",
        "global animator_duration_scale 0",
        // Disable keyguard completely to avoid issue logs.
        "secure lockscreen.disabled 1",
        // Disable all sound. Otherwise logcat get polluted by audio_hw_generic: Hardware backing HAL too slow
        "system sound_effects_enabled 0"
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
        "com.google.android.calendar",
        // android 24
        "com.google.android.partnersetup",
        // android 30
        "com.google.android.apps.maps",
        "com.google.android.apps.wellbeing",

        // Reduce log spam.
        "com.android.dialer",
        "com.google.android.apps.messaging",
        "com.android.phone", // Has no reducing effect ??
        "com.google.android.talk",
        "com.google.android.googlequicksearchbox",
        "com.google.android.configupdater",
        "com.android.bluetooth",
        "com.android.carrierconfig",
        "com.android.carrierdefaultapp",
        "com.android.providers.telephony",
        "com.android.providers.calendar",
        "com.google.android.calendar",
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
    override fun disableServices() = listOf(
        "com.google.android.gms/com.google.android.location.geofencer.service.GeofenceProviderService",
        "android/.hardware.location.GeofenceHardwareService",

        // Disabling telephony services didn't work.
        // "com.android.phone/com.android.internal.telephony.CellularNetworkService",
        // "com.android.phone/com.android.internal.telephony.dataconnection.CellularDataService",
        // "com.android.phone/com.android.services.telephony.sip.components.TelephonySipService",

        // Disabling telecom services didn't work.
        // "com.android.server.telecom/.components.BluetoothPhoneService",
        // "com.android.server.telecom/.components.TelecomService",

        // on android 16 and 18 this force random crashing gsm
        "com.google.android.gms/.chimera.GmsApiService",
        "com.google.android.gms/.chimera.PersistentApiService",
        "com.google.android.gms/com.google.android.location.internal.server.HardwareArProviderService",
        "com.google.android.gms/com.google.location.nearby.direct.service.NearbyDirectService",
        "com.google.android.gms/.nearby.discovery.service.DiscoveryService",
        "com.google.android.gms/.nearby.messages.service.NearbyMessagesService",
        "com.google.android.gms/.deviceconnection.service.DeviceConnectionWatcherService",
        "com.google.android.gms/com.google.android.location.internal.GoogleLocationManagerService",
        "com.google.android.gms/com.google.android.location.network.NetworkLocationService",
        "com.google.android.gms/.common.stats.GmsCoreStatsService",
        "com.google.android.gms/com.google.android.contextmanager.service.ContextManagerService",
        "com.google.android.gms/.chimera.PersistentDirectBootAwareApiService",
        "com.google.android.gms/.thunderbird.EmergencyPersistentService",
        "com.google.android.gms/.gcm.nts.SchedulerService",
        "com.google.android.gms/com.google.android.location.fused.FusedLocationService",
        "com.google.android.gms/.fido.fido2.pollux.CableAuthenticatorService",
        "com.google.android.gms/.chimera.PersistentBoundBrokerService",
        "com.google.android.gms/.clearcut.debug.ClearcutDebugDumpService",
        "com.google.android.gms/.gcm.GcmService",
        "com.google.android.gms/com.google.android.location.geocode.GeocodeService",
        "com.google.android.gms/.location.persistent.LocationPersistentService",
        "com.google.android.gms/com.google.android.location.internal.server.GoogleLocationService",
        "com.google.android.gms/.location.persistent.LocationPersistentService",
        "com.google.android.gms/.auth.setup.devicesignals.LockScreenService",
        "com.google.android.gms/.chimera.GmsIntentOperationService",
        "com.google.android.gms/.auth.account.authenticator.GoogleAccountAuthenticatorService",
    )
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