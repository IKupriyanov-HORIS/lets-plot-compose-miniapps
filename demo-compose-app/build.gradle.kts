import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform") // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
    id("org.jetbrains.compose")
}

val lets_plot_version: String by project

kotlin {
    jvm {}
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":vis-svg-mapper-skia"))
                implementation(project(":vis-swing-skia"))
                implementation("org.jetbrains.lets-plot:mapper-core:$lets_plot_version") // Mapper
                implementation("org.jetbrains.lets-plot:base:$lets_plot_version") // HasParent
                implementation("org.jetbrains.lets-plot:vis-svg-portable:$lets_plot_version")
                implementation("org.jetbrains.lets-plot:base-portable:$lets_plot_version") // SimpleComposite
                implementation("org.jetbrains.lets-plot:plot-config-portable:$lets_plot_version") //
                implementation("org.jetbrains.lets-plot:vis-svg-mapper:$lets_plot_version") // runtime: SvgNodeSubtreeGeneratingSynchronizer
                implementation("org.jetbrains.lets-plot:vis-swing-common:$lets_plot_version")
                implementation("org.jetbrains.lets-plot:plot-demo-common:$lets_plot_version")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "demo-compose-app"
            packageVersion = "1.0.0"
        }
    }
}
