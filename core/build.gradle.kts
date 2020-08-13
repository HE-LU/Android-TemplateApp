plugins {
    id("com.android.library")
    id("common-binary-plugin")
}

android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    // Title: Others
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
}
