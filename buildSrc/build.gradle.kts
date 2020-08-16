plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("common-binary-plugin") {
            id = "common-binary-plugin"
            implementationClass = "cz.helu.plugins.CommonBinaryPlugin"
        }
    }
}

buildscript {
    repositories {
        google()
        jcenter()
    }
}

repositories {
    jcenter()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.1")
    implementation("com.android.tools.build:gradle-api:4.0.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
}
