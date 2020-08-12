plugins {
    id("com.android.library")
    id("common-binary-plugin")
}

dependencies {
    // Title: Others
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
}
