plugins {
    id("io.gitlab.arturbosch.detekt").version(Detekt.VERSION)
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("common-binary-plugin")
}

android {
    defaultConfig {
        applicationId = Config.APPLICATION_ID
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("boolean", "LOGS", "true")
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            ext.set("enableCrashlytics", false)
            ext.set("alwaysUpdateBuildId", false)
        }

        getByName("release") {
            buildConfigField("boolean", "LOGS", "false")
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isZipAlignEnabled = true
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":core"))

    implementation(project(":feature-debug-tools"))

    // Title: AndroidX
    implementation(AndroidX.LIFECYCLE_RUNTIME)
    implementation(AndroidX.LIFECYCLE_EXTENSIONS)
    implementation(AndroidX.LIFECYCLE_VIEWMODEL)
    implementation(AndroidX.LIFECYCLE_LIVEDATA)
    kapt(AndroidX.LIFECYCLE_COMPILER)

    // Title: Hilt
    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_COMPILER)

    // Title: Retrofit
    implementation(Retrofit.OKHTTP)
    implementation(Retrofit.OKHTTP_LOGGING_INTERCEPTOR)
    implementation(Retrofit.RETROFIT)

    // Title: Others
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
}

// Title: Detekt init
detekt {
    config = files("${project.rootDir}/extras/detekt.yml")
    parallel = true
}

// Title: Ktlint init
ktlint {
    version.set(Ktlint.VERSION)
    android.set(true)
    disabledRules.addAll("max-line-length", "curly-spacing")
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}
