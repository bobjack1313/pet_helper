plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.csce5430sec7proj.pethelper"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.csce5430sec7proj.pethelper"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

val dbflow_version = "5.0.0-alpha2"
//val sqlcipherVersion = "1.0"

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    kapt("com.github.agrosner.dbflow:processor:${dbflow_version}")
    implementation("com.github.agrosner.dbflow:core:${dbflow_version}")
    implementation("com.github.agrosner.dbflow:lib:${dbflow_version}")
    // sql-cipher database encryption (optional)
    //implementation("com.github.agrosner.dbflow:sqlcipher:${dbflow_version}")
    //implementation("net.zetetic:android-database-sqlcipher:${sqlcipher_version}@aar")
    // RXJava 2 support
    implementation("com.github.agrosner.dbflow:reactive-streams:${dbflow_version}")
    // Kotlin Coroutines
    implementation("com.github.agrosner.dbflow:coroutines:${dbflow_version}")
    // Android Architecture Components Paging Library Support
    implementation("com.github.agrosner.dbflow:paging:${dbflow_version}")
    // Android Architecture Components LiveData Library Support
    implementation("com.github.agrosner.dbflow:livedata:${dbflow_version}")
    // adds generated content provider annotations + support.
    implementation("com.github.agrosner.dbflow:contentprovider:${dbflow_version}")
}