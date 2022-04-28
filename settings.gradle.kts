pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)
        id("com.android.base").version(agpVersion)
        id("com.android.application").version(agpVersion)
    }
}

include(":vis-svg-mapper-skia")
include(":swing-app")
include(":android-app")

project(":vis-svg-mapper-skia").projectDir = File("./vis-svg-mapper-skia")
project(":swing-app").projectDir = File("./swing-app")
project(":android-app").projectDir = File("./android-app")
