plugins {
    application
    kotlin("jvm")
}

group = "ikupriyanov"
version = "1.0-SNAPSHOT"


val lets_plot_version: String by project
val skikoVersion = extra["skiko.version"] as String

val osName = System.getProperty("os.name")
val hostOs = when {
    osName == "Mac OS X" -> "macos"
    osName.startsWith("Win") -> "windows"
    osName.startsWith("Linux") -> "linux"
    else -> error("Unsupported OS: $osName")
}

val osArch = System.getProperty("os.arch")
var hostArch = when (osArch) {
    "x86_64", "amd64" -> "x64"
    "aarch64" -> "arm64"
    else -> error("Unsupported arch: $osArch")
}

val host = "${hostOs}-${hostArch}"


dependencies {
    implementation("org.jetbrains.skiko:skiko:$skikoVersion")
    implementation(project(":vis-svg-mapper-skia"))
    implementation("org.jetbrains.lets-plot:base-portable:$lets_plot_version")
    implementation("org.jetbrains.lets-plot:base:$lets_plot_version")
    implementation("org.jetbrains.lets-plot:mapper-core:$lets_plot_version")
    implementation("org.jetbrains.lets-plot:vis-svg-portable:$lets_plot_version")
    implementation("org.jetbrains.lets-plot:vis-svg-mapper:$lets_plot_version")
    implementation("org.jetbrains.lets-plot:vis-canvas:$lets_plot_version")
    implementation("org.jetbrains.lets-plot:vis-swing-common:$lets_plot_version")
    implementation("org.jetbrains.lets-plot:plot-builder-portable:$lets_plot_version")
    implementation("org.jetbrains.lets-plot:plot-builder:$lets_plot_version")
    implementation("org.jetbrains.lets-plot:plot-config-portable:$lets_plot_version")
    implementation("org.jetbrains.lets-plot:plot-config:$lets_plot_version")
    implementation("org.jetbrains.skiko:skiko-awt-runtime-$hostOs-$hostArch:$skikoVersion")
}
