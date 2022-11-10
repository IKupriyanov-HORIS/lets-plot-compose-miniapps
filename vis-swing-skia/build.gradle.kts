plugins {
    application
    kotlin("jvm")
}

group = "ikupriyanov"
version = "1.0-SNAPSHOT"


val lets_plot_version: String by extra
val skiko_version: String by extra

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
    implementation(project(":vis-svg-mapper-skia"))
    implementation("org.jetbrains.skiko:skiko:$skiko_version")
    implementation("org.jetbrains.lets-plot:base-portable:$lets_plot_version") { isTransitive = false }
    implementation("org.jetbrains.lets-plot:plot-config-portable:$lets_plot_version") { isTransitive = false }
    implementation("org.jetbrains.lets-plot:vis-svg-portable:$lets_plot_version") { isTransitive = false }
    implementation("org.jetbrains.lets-plot:lets-plot-batik:$lets_plot_version") { isTransitive = false }
}
