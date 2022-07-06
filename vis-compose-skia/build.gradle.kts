import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform") // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
    id("org.jetbrains.compose")

}

group = "ikupriyanov"
version = "1.0-SNAPSHOT"
val lets_plot_version: String by project

kotlin {
    jvm {}
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":vis-svg-mapper-skia"))
                implementation("org.jetbrains.lets-plot:mapper-core:$lets_plot_version") // Mapper
                implementation("org.jetbrains.lets-plot:base:$lets_plot_version") // HasParent
                implementation("org.jetbrains.lets-plot:vis-svg-portable:$lets_plot_version")
                implementation("org.jetbrains.lets-plot:base-portable:$lets_plot_version") // SimpleComposite
                implementation("org.jetbrains.lets-plot:vis-svg-mapper:$lets_plot_version") // runtime: SvgNodeSubtreeGeneratingSynchronizer
            }
        }
    }
}
repositories {
    mavenCentral()
}

