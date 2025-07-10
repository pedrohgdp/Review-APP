plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.reviewgame"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.reviewgame"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    packaging {
        resources {
            pickFirsts += "META-INF/INDEX.LIST"
            // Você também pode precisar adicionar outras exclusões ou escolhas aqui
            // dependendo de outros conflitos que possam surgir.
            // Exemplo de exclusão comum (se necessário para outros arquivos):
            excludes += "META-INF/DEPENDENCIES"
            // excludes += "META-INF/LICENSE"
            // excludes += "META-INF/LICENSE.txt"
            // excludes += "META-INF/NOTICE"
            // excludes += "META-INF/NOTICE.txt"
            // excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.genai:google-genai:1.0.0")
}