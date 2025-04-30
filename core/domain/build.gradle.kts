plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.tbc.bookli.core.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Libraries
    implementation(libs.androidx.core.ktx) // Bad practice, because it is Android related
    implementation(libs.androidx.paging.common.android) // Bad practice, because it is Android related
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
}