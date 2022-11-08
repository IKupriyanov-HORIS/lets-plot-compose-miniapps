pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        val compose_version: String by extra
        val kotlin_version: String by extra
        val agp_version: String by extra

        kotlin("jvm").version(kotlin_version)
        kotlin("android").version(kotlin_version).apply(false)
        id("org.jetbrains.compose").version(compose_version).apply(false)
        id("com.android.base").version(agp_version).apply(false)
        id("com.android.application").version(agp_version).apply (false)
    }
}

include(":vis-svg-mapper-skia")
include(":demo-swing-app")
include(":demo-android-app")
include(":demo-compose-app")
include("vis-swing-skia")

project(":vis-svg-mapper-skia").projectDir = File("./vis-svg-mapper-skia")
project(":vis-swing-skia").projectDir = File("./vis-swing-skia")
project(":demo-swing-app").projectDir = File("./demo-swing-app")
project(":demo-android-app").projectDir = File("./demo-android-app")
project(":demo-compose-app").projectDir = File("./demo-compose-app")
include("vis-compose-skia")
