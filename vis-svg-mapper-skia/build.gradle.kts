/*
 * Copyright (c) 2019. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("maven-publish")
}

kotlin {
    jvm()
    sourceSets {
        commonMain {
            dependencies {
                val lets_plot_version: String by extra
                val skiko_version: String by extra

                compileOnly("org.jetbrains.skiko:skiko:$skiko_version")
                compileOnly("org.jetbrains.lets-plot:lets-plot-batik:$lets_plot_version")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
