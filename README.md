# lets-plot-skia-mapper

Rendering lets-plot with Skia. This project is a WIP.

![img.png](img.png)
### Local configuration for Lets-Plot project.
Skia mapper requires additional modules that have to be published. Also Kotlin version should be set to 1.7.10. This patch meets all these requirements:
```
Index: build.gradle
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>UTF-8
===================================================================
diff --git a/build.gradle b/build.gradle
--- a/build.gradle	(revision abafb0398a7c25d73c2e824a6237d4bd0a8b7b30)
+++ b/build.gradle	(date 1664461354177)
@@ -180,6 +180,15 @@
 subprojects {
     // Configure publishing for projects which "Lets-Plot Kotlin API" depends on.
     if (name in [
+            'base',
+            'plot-builder',
+            'vis-swing-common',
+            'plot-config',
+            'plot-demo-common',
+            'vis-demo-common',
+            'vis-svg-mapper', // for Android, can't be used from lets-plot-batik
+            'mapper-core', // for Android, can't be used from lets-plot-batik
+            'vis-canvas', // for Android, can't be used from lets-plot-batik
             'plot-base-portable',
             'base-portable',
             'vis-svg-portable',
Index: gradle.properties
IDEA additional info:
Subsystem: com.intellij.openapi.diff.impl.patch.CharsetEP
<+>ISO-8859-1
===================================================================
diff --git a/gradle.properties b/gradle.properties
--- a/gradle.properties	(revision abafb0398a7c25d73c2e824a6237d4bd0a8b7b30)
+++ b/gradle.properties	(date 1664390502240)
@@ -6,7 +6,7 @@
 kotlin.code.style=official
 
 
-kotlin_version=1.6.21
+kotlin_version=1.7.10
 kotlinLogging_version=2.0.5
 idea_ext_version=0.7
```
After that run the following command:
> ./gradlew publishAllPublicationsToMavenLocalRepository

### Local configuration for Skia mapper project.
Add the following property `maven.repo.local=<LETS-PLOT-PROJECT-ROOT>/.maven-publish-dev-repo` to the `local.properties` file.


### Android demo configuration.

With `SDK Manager` from menu "Tools -> Android -> SDK Manager" setup Android SDK. `local.properties` file should be automatically generate. Otherwise add it manually with proper path (like `sdk.dir=/Users/john/Library/Android/sdk`).  

With `Device Manager` from "Tools -> Android -> Device Manager" setup virtual device.   

Nexus 5 with Android 12 works well.

Select `demo-android-app` in `Run configurations` to run it.

#### Known issues
Sometimes build fails with messages like:
> Module was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.7.1, expected version is 1.5.1.

Running the build task one more time fixes the problem.

### Compose demo

To run compose demo use gradle task `demo-compose-app -> Tasks -> compose desktop -> run`. Running `main` function with gutter button is not working. To run another demo change the `compose.desktop.application.mainClass` property in `build.gradle.kts`.