plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("common-binary-plugin")
}

android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    // Title: Hilt
    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_COMPILER)

    // Title: Glide
    implementation(Glide.GLIDE)
    implementation(Glide.GLIDE_OKHTTP3)
    kapt(Glide.GLIDE_COMPILER)

    // Title: Others
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
}
