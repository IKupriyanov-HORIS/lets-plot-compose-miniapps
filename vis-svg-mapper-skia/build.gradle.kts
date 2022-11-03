/*
 * Copyright (c) 2019. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("maven-publish")
}

val lets_plot_version: String by extra
val skiko_version: String by extra

kotlin {
    jvm {
    }

    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                compileOnly("org.jetbrains.skiko:skiko:$skiko_version")

                // batik dep causes error:
                /*
                > Could not resolve all dependencies for configuration ':vis-svg-mapper-skia:jsNpm'.
                    > Could not resolve org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3.
                      Required by:
                        project :vis-svg-mapper-skia > org.jetbrains.lets-plot:lets-plot-batik:2.5.1-alpha1
                 */
                //compileOnly("org.jetbrains.lets-plot:lets-plot-batik:$lets_plot_version")

                compileOnly("org.jetbrains.lets-plot:base-portable:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:base:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:mapper-core:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:vis-svg-portable:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:vis-svg-mapper:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:vis-canvas:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:plot-builder-portable:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:plot-builder:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:plot-config-portable:$lets_plot_version")
                compileOnly("org.jetbrains.lets-plot:plot-config:$lets_plot_version")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
