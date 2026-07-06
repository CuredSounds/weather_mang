plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
}

kotlin {
    jvm("desktop")
    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(project(":domain"))
                implementation(project(":core-data"))
                implementation(project(":core-ui"))
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)
                implementation(libs.ktor.client.core)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg)
            packageName = "WeatherMang"
            packageVersion = "1.0.0"
        }
    }
}
