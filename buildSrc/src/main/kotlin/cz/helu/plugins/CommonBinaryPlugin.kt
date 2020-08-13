package cz.helu.plugins

import AndroidX
import Config
import Jvm
import Kotlin
import Signing
import Timber
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

class CommonBinaryPlugin : Plugin<Project> {
    private val Project.android: BaseExtension
        get() = extensions.findByName("android") as? BaseExtension
            ?: error("Not an Android module: $name")

    override fun apply(project: Project) {
        with(project) {
            configureSigningConfig(this)
            configurePlugins()
            configureAndroid()
            configureKapt()
            configureDependencies()
        }
    }

    private fun Project.configureKapt() {
        tasks.withType<KotlinCompile> {
            kotlinOptions.jvmTarget = Jvm.VERSION
            kotlinOptions.freeCompilerArgs += listOf(
                "-Xuse-experimental=kotlin.Experimental",
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlin.ExperimentalUnsignedTypes"
            )
        }

        configure<KaptExtension> {
            javacOptions {
                // increase number of errors (helps databinding with errors)
                option("-Xmaxerrs", 500)
            }
        }
    }

    private fun Project.configurePlugins() {
        plugins.run {
            apply("kotlin-android")
            apply("kotlin-android-extensions")
            apply("kotlin-kapt")
        }
    }

    private fun Project.configureSigningConfig(project: Project) {
        Signing.initialize(project)

        android.run {
            // Subtitle: Signing config settings
            signingConfigs {
                create("release") {
                    val signingValues = Signing.signingValues()
                    storeFile = File(signingValues.storeFilePath)
                    storePassword = signingValues.storePassword
                    keyAlias = signingValues.keyAlias
                    keyPassword = signingValues.keyPassword
                }
            }
        }
    }

    private fun Project.configureAndroid() {
        android.run {
            compileSdkVersion(Config.SDK_COMPILE_VERSION)
            buildToolsVersion(Config.BUILD_TOOLS_VERSION)

            // Subtitle: Default config settings
            defaultConfig {
                targetSdkVersion(Config.SDK_TARGET_VERSION)
                minSdkVersion(Config.SDK_MIN_VERSION)

                // versionCode is generated by CI build process
                versionCode = 1
                versionName = "${Config.VERSION_MAJOR}.${Config.VERSION_MINOR}.${Config.VERSION_PATCH}"

                // Set output APK filename
                setProperty("archivesBaseName", "${Config.APPLICATION_ID}-v${versionName}(${versionCode})")
            }

            // Subtitle: Compile options
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            // Subtitle: Increase the build performance but also increases the APK size
            aaptOptions {
                cruncherEnabled = false
            }

            // Subtitle: To increase build performance
            dexOptions {
                preDexLibraries = true
                maxProcessCount = 8
            }

            // Subtitle: Set Kotlin sourceSets
            sourceSets["main"].java.srcDir("src/main/kotlin")

            // Subtitle: Flavors and build types
            flavorDimensions("env")

            productFlavors {
                create("develop") {
                    dimension = "env"
                    buildConfigField("boolean", "DEV_ENVIRONMENT", "true")
                }
                create("staging") {
                    dimension = "env"
                    buildConfigField("boolean", "DEV_ENVIRONMENT", "false")
                }
                create("production") {
                    dimension = "env"
                    buildConfigField("boolean", "DEV_ENVIRONMENT", "false")
                }
            }
        }
    }

    private fun Project.configureDependencies() {
        dependencies {
            // Title: AndroidX
            "implementation"(AndroidX.CORE)
            "implementation"(AndroidX.APPCOMPAT)
            "implementation"(AndroidX.FRAGMENT)
            "implementation"(AndroidX.CARDVIEW)
            "implementation"(AndroidX.COLLECTION)
            "implementation"(AndroidX.CONSTRAINT_LAYOUT)
            "implementation"(AndroidX.MATERIAL)

            // Title: Kotlin coroutines
            "implementation"(Kotlin.CORUTINES_CORE)
            "implementation"(Kotlin.CORUTINES_ANDROID)

            // Title: Navigation
            "implementation"(AndroidX.NAVIGATION_FRAMGMET)
            "implementation"(AndroidX.NAVIGATION_UI)

            // Title: Others
            "implementation"(Timber.TIMBER)
        }
    }
}