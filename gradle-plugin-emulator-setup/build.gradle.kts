plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("emulator") {
            id = "de.nenick.android.emulator-setup"
            implementationClass = "de.nenick.android.emulator.EmulatorSetupPlugin"
        }
    }
}

repositories {
    jcenter()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.1.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
}