import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("pokedex.android.application.jacoco")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.z1.pokedex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.z1.pokedex"
        minSdk = 26
        targetSdk = 34
        versionCode = 10
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "WEB_CLIENT_ID", getProperties("WEB_CLIENT_ID"))

        ksp {
            arg("room.schemaLocation", "$projectDir/src/main/java/com/z1/pokedex/core/database/schema")
        }
    }

    buildTypes {
        release {
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    ktlint {
        additionalEditorconfigFile.set(file("${project.rootDir}/.editorConfig"))
        disabledRules.set(setOf("no-wildcard-imports", "import-ordering"))
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    // Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // Jetpack compose
    implementation(platform("androidx.compose:compose-bom:2024.04.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.5")

    implementation("androidx.compose.ui:ui-graphics:1.7.0-alpha08")
    implementation("androidx.navigation:navigation-compose:2.8.0-alpha08")
    implementation("androidx.compose.animation:animation:1.7.0-alpha08")

    // Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    implementation("androidx.paging:paging-compose:3.2.1")

    // Koin
    implementation("io.insert-koin:koin-android:3.1.3")
    implementation("io.insert-koin:koin-androidx-compose:3.1.3")

    // Moshi converter
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Logging interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Palette
    implementation("androidx.palette:palette-ktx:1.0.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-auth")

    // Play Services Auth
    implementation("com.google.android.gms:play-services-auth:21.0.0")

    // Billing
    implementation("com.android.billingclient:billing-ktx:6.2.1")

    // MockK
    testImplementation("io.mockk:mockk-android:1.13.11")
    testImplementation("io.mockk:mockk-agent:1.13.11")

    // Test
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

fun getProperties(propertiesName: String): String {
    return runCatching {
        val propsFile = rootProject.file("local.properties")
        if (propsFile.exists()) {
            val properties = Properties()
            properties.load(FileInputStream(propsFile))
            properties.getProperty(propertiesName)
        } else {
            ""
        }
    }.getOrDefault("")
}