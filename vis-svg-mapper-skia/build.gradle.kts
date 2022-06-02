/*
 * Copyright (c) 2019. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("maven-publish")
    //id("org.jetbrains.compose")
}

kotlin {
    jvm()
    sourceSets {
        commonMain {
            dependencies {
                val lets_plot_version: String by project
                val skikoVersion = extra["skiko.version"] as String

                compileOnly("org.jetbrains.lets-plot:base:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:base-portable:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:mapper-core:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:vis-svg-portable:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:vis-svg-mapper:$lets_plot_version")

                compileOnly("org.jetbrains.skiko:skiko:$skikoVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
