plugins {
    application
    kotlin("jvm")
}

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
    implementation("org.jetbrains.skiko:skiko:$skiko_version")
    implementation("org.jetbrains.skiko:skiko-awt-runtime-$hostOs-$hostArch:$skiko_version")
    implementation(project(":vis-svg-mapper-skia"))
    implementation(project(":vis-swing-skia"))

    implementation("org.jetbrains.lets-plot:lets-plot-batik:$lets_plot_version")
    implementation("org.jetbrains.lets-plot:plot-demo-common:$lets_plot_version")
}
