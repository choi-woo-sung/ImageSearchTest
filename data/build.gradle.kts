plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
//    id 'kotlin-kapt'
//    id 'de.mannodermaus.android-junit5'
}

android {
    namespace = "com.woosung.data"
    compileSdk = libs.versions.targetSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))

//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4'

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)



    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.kotlinx.serialization)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

//    testImplementation "org.junit.jupiter:junit-jupiter-api:5.8.2"
//    testRuntimeOnly "org.junit.jupiter:junit-jupiter-engine:5.8.2"
//    testRuntimeOnly "org.junit.vintage:junit-vintage-engine:5.8.2"

}
