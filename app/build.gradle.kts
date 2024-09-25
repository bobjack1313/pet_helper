plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
   // id("com.google.devtools.ksp")
}

kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.csce5430sec7proj.pethelper"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.csce5430sec7proj.pethelper"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

val dbflowVersion = "5.0.0-alpha2"
//val sqlcipherVersion = "1.0"

dependencies {
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
   // ksp("androidx.room:room-compiler:2.6.1")

    implementation("com.google.dagger:dagger-compiler:2.51.1")
    //ksp("com.google.dagger:dagger-compiler:2.51.1")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    kapt("com.github.agrosner.dbflow:processor:${dbflowVersion}")
    //annotationProcessor("com.github.agrosner.dbflow:processor:${dbflowVersion}")
    implementation("com.github.agrosner.dbflow:core:${dbflowVersion}")
    implementation("com.github.agrosner.dbflow:lib:${dbflowVersion}")
    // sql-cipher database encryption (optional)
    //implementation("com.github.agrosner.dbflow:sqlcipher:${dbflow_version}")
    //implementation("net.zetetic:android-database-sqlcipher:${sqlcipher_version}@aar")
    // RXJava 2 support
    implementation("com.github.agrosner.dbflow:reactive-streams:${dbflowVersion}")
    // Kotlin Coroutines
    implementation("com.github.agrosner.dbflow:coroutines:${dbflowVersion}")
    // Android Architecture Components Paging Library Support
    implementation("com.github.agrosner.dbflow:paging:${dbflowVersion}")
    // Android Architecture Components LiveData Library Support
    implementation("com.github.agrosner.dbflow:livedata:${dbflowVersion}")
    // adds generated content provider annotations + support.
    implementation("com.github.agrosner.dbflow:contentprovider:${dbflowVersion}")
}