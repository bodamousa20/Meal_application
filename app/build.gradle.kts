plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.google.gms.google.services) // Apply kapt plugin
}

android {
    namespace = "com.example.meals_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.meals_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.0")

    // SDP and SSP libraries
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    implementation("com.intuit.ssp:ssp-android:1.0.6")

    // GIF Drawable library
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.24")

    // Retrofit and Glide
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")

    // Room Database
    implementation("androidx.room:room-ktx:2.6.0") // Update Room to latest stable version
    implementation("androidx.room:room-runtime:2.6.0")
    implementation(libs.firebase.auth)
    implementation(libs.androidx.lifecycle.viewmodel.android) // Ensure version consistency
    kapt("androidx.room:room-compiler:2.6.0") // Use the same version for compiler
    implementation ("androidx.recyclerview:recyclerview:1.2.1" )// Check for the latest version


    // MVVM
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0") // Update to latest stable
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0") // Update to latest stable

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0") // Update to latest stable
    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0")

    // fireBase Dynamic Login
    implementation ("com.google.firebase:firebase-dynamic-module-support:16.0.0-beta03")
    implementation ("com.google.android.gms:play-services-auth:21.2.0")

    //splach screen
    implementation ("androidx.core:core-splashscreen:1.0.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
