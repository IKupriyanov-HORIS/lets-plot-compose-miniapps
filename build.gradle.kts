import org.yaml.snakeyaml.Yaml

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


if (project.file("build_settings.yml").exists()) {
    println("parsing build settings")
    project.extra["buildSettings"] = Yaml().load(project.file("build_settings.yml").inputStream())
} else {
    println("no build settings")
}

allprojects {
    group = "ikupriyanov"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        val buildSettings: Map<String, Any?>? by project

        buildSettings?.get("mavenDevRepo")?.let {
            maven {
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
