plugins {
    id("io.gitlab.arturbosch.detekt") version Detekt.VERSION
}

buildscript {
    repositories {
        google()
        maven("https://plugins.gradle.org/m2/") // Usage: Ktlint
    }

    dependencies {
        classpath(Gradle.GRADLE_PLUGIN)
        classpath(Kotlin.KOTLIN_PLUGIN)
        classpath(Ktlint.KTLINT_PLUGIN)
        classpath(Hilt.HILT_PLUGIN)
    }
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        google()
        jcenter()
    }
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    // Title: Detekt init
    detekt {
        config = files("${project.rootDir}/extras/detekt.yml")
        parallel = true
    }
}
