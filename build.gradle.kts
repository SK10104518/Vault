plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("kotlin-kapt") // For Room annotations
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
}

android {
    namespace = "com.example.vault"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.vault"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildFeatures {
            viewBinding = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Room DB
    //implementation ("androidx.room:room-runtime:2.7.1")
    implementation (libs.androidx.room.runtime)
    //kapt ("androidx.room:room-compiler:2.7.1")
    ksp (libs.androidx.room.compiler)
    //implementation ("androidx.room:room-ktx:2.7.1") // Kotlin Extensions for Room
    implementation (libs.room.ktx) // Kotlin Extensions for Room

    // Coroutines (for Room DB operations off main thread)
    //implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation (libs.kotlinx.coroutines.core)
    //implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation (libs.jetbrains.kotlinx.coroutines.android)

    // Lifecycle components (ViewModel, LiveData)
    //implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0")
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    //implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.9.0")
    implementation (libs.lifecycle.livedata.ktx)

    // CameraX (for taking photos)
    //implementation ("androidx.camera:camera-core:1.4.2")
    implementation (libs.androidx.camera.core)
    //implementation ("androidx.camera:camera-camera2:1.4.2")
    implementation (libs.camera.camera2)
    //implementation ("androidx.camera:camera-lifecycle:1.4.2")
    implementation (libs.camera.lifecycle)
    //implementation ("androidx.camera:camera-view:1.4.2")
    implementation (libs.camera.view)
    //implementation ("androidx.camera:camera-extensions:1.4.2")
    implementation (libs.camera.extensions)

    // For image handling (e.g., Coil)
    //implementation("io.coil-kt:coil:2.6.0")
    implementation(libs.coil)

   //implementation ("androidx.navigation:navigation-fragment-ktx:2.9.0")
    //implementation ("androidx.navigation:navigation-ui-ktx:2.9.0")
    //implementation ("com.google.android.material:material:1.12.0")
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)
    implementation (libs.material)

    //implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation(libs.mpandroidchart)




}