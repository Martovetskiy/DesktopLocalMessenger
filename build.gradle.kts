import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.6.0"
}

group = "org.martovetskiy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.components.resources)
    implementation("com.arkivanov.decompose:decompose:3.0.0")
    implementation("com.arkivanov.decompose:extensions-compose:3.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "local_network_messenger"
            packageVersion = "1.0.0"
        }
    }
}
