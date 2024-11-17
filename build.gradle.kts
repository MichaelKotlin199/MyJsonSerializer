plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // JSON libraries for comparison
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    // Testing and benchmarking
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.10")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("MainKt")
}
