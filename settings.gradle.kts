pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        val composeVersion = extra["compose.version"] as String
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)
        id("org.jetbrains.compose").version(composeVersion)
        id("com.android.base").version(agpVersion)
        id("com.android.application").version(agpVersion)
    }
}

include(":vis-svg-mapper-skia")
include(":demo-swing-app")
include(":demo-android-app")
include(":demo-compose-app")

project(":vis-svg-mapper-skia").projectDir = File("./vis-svg-mapper-skia")
project(":demo-swing-app").projectDir = File("./demo-swing-app")
project(":demo-android-app").projectDir = File("./demo-android-app")
project(":demo-compose-app").projectDir = File("./demo-compose-app")
