import java.util.*

buildscript {

    dependencies {
        classpath("org.yaml:snakeyaml:1.25")
    }
}

plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
}

val localProps = Properties()
if (project.file("local.properties").exists()) {
    localProps.load(project.file("local.properties").inputStream())
    project.extra["local"] = localProps
}

allprojects {
    group = "ikupriyanov"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        val buildSettings: Map<String, Any?>? by project

        localProps.get("maven.repo.local")?.let {
            mavenLocal {
                println("${project.name}: local maven repo: $it")
                url = uri(it)
            }
        }
    }
}

subprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}
