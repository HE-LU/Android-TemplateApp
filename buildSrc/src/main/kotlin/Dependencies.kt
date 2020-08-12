object Jvm {
    const val VERSION = "1.8"
}

object Gradle {
    // Subtitle: Versions
    const val GRADLE_PLUGIN_VERSION = "4.0.1"

    // Subtitle: Dfinitions
    const val GRADLE_PLUGIN = "com.android.tools.build:gradle:$GRADLE_PLUGIN_VERSION"
}

object AndroidX {
    // Subtitle: Versions
    const val CORE_VERSION = "1.3.1"
    const val APPCOMPAT_VERSION = "1.2.0"
    const val COLLECTION_VERSION = "1.1.0"
    const val FRAGMENT_VERSION = "1.2.5"
    const val CONSTRAINT_LAYOUT_VERSION = "1.1.3"
    const val CARDVIEW_VERSION = "1.0.0"
    const val LIFECYCLE_VERSION = "2.2.0"
    const val NAVIGATION_VERSION = "2.3.0"
    const val MATERIAL_VERSION = "1.2.0"

    // Subtitle: Dfinitions
    const val CORE = "androidx.core:core-ktx:$CORE_VERSION"
    const val APPCOMPAT = "androidx.appcompat:appcompat:$APPCOMPAT_VERSION"
    const val COLLECTION = "androidx.collection:collection-ktx:$COLLECTION_VERSION"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:$FRAGMENT_VERSION"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION"
    const val CARDVIEW = "androidx.cardview:cardview:$CARDVIEW_VERSION"

    const val LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VERSION"
    const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION"
    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION"
    const val LIFECYCLE_COMPILER = "androidx.lifecycle:lifecycle-compiler:$LIFECYCLE_VERSION"

    const val NAVIGATION_FRAMGMET = "androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION"
    const val MATERIAL = "com.google.android.material:material:$MATERIAL_VERSION"
}

object Kotlin {
    // Subtitle: Versions
    const val KOTLIN_VERSION = "1.3.72"
    const val CORUTINES_VERSION = "1.3.8"

    // Subtitle: Dfinitions
    const val KOTLIN_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.KOTLIN_VERSION}"
    const val CORUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$CORUTINES_VERSION"
    const val CORUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$CORUTINES_VERSION"
}

object Retrofit {
    // Subtitle: Versions
    const val OKHTTP_VERSION = "4.8.1"
    const val RETROFIT_VERSION = "2.9.0"

    // Subtitle: Dfinitions
    const val OKHTTP = "com.squareup.okhttp3:okhttp:$OKHTTP_VERSION"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
}

object Glide {
    // Subtitle: Versions
    const val GLIDE_VERSION = "4.11.0"

    // Subtitle: Dfinitions
    const val GLIDE = "com.github.bumptech.glide:glide:$GLIDE_VERSION"
    const val GLIDE_OKHTTP3 = "com.github.bumptech.glide:okhttp3-integration:$GLIDE_VERSION"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:$GLIDE_VERSION"
}

object Timber {
    // Subtitle: Versions
    const val TIMBER_VERSION = "4.7.1"

    // Subtitle: Dfinitions
    const val TIMBER = "com.jakewharton.timber:timber:$TIMBER_VERSION"
}

object Detekt {
    // Subtitle: Versions
    const val VERSION = "1.11.0-RC2"
}

object Ktlint {
    // Subtitle: Versions
    const val VERSION = "0.37.2"
    const val KTLINT_PLUGIN_VERSION = "9.3.0"

    // Subtitle: Dfinitions
    const val KTLINT_PLUGIN = "org.jlleitschuh.gradle:ktlint-gradle:$KTLINT_PLUGIN_VERSION"
}