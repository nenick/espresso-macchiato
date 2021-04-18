package de.nenick.android.emulator.setup

import java.lang.IllegalStateException
import kotlin.reflect.KClass

sealed class AndroidSetup {

    protected abstract fun androidApi(): Int
    protected abstract fun systemImageAbi(): String
    fun systemImage() = "system-images;android-${androidApi()};google_apis;${systemImageAbi()}"

    abstract fun createAvdAdditionalArgs(): Array<out String>

    abstract fun avdSetting(): Array<String>

    companion object {
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
}

object Android18 : DefaultPreAndroid20Setup() {
    override fun androidApi() = 18
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
}