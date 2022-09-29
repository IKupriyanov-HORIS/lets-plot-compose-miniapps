import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform") // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
    id("org.jetbrains.compose")
}

val lets_plot_version: String by extra

kotlin {
    jvm {}
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":vis-svg-mapper-skia"))
                implementation(project(":vis-swing-skia"))

                implementation("org.jetbrains.lets-plot:lets-plot-batik:$lets_plot_version")
                implementation("org.jetbrains.lets-plot:plot-demo-common:$lets_plot_version")
            }
        }
    }
}

compose.desktop.application {
    mainClass = "me.ikupriyanov.demo.plot.AreaViewerSkiaKt"

    nativeDistributions {
        targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
        packageName = "lets-plot-compose-demo-app"
        packageVersion = "1.0.0"
    }
}
