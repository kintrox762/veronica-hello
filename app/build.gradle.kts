plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android {
    defaultConfig {
        applicationId = "com.you.veronica"
        versionCode = (System.getenv("VER_CODE") ?: "1").toInt()
        versionName = System.getenv("VER_NAME") ?: "1.0"
    }

    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("SIGN_STORE_FILE") ?: "veronica.keystore")
            storePassword = System.getenv("SIGN_STORE_PASS")
            keyAlias = System.getenv("SIGN_KEY_ALIAS")
            keyPassword = System.getenv("SIGN_KEY_PASS")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
        }
        debug {
            signingConfig = signingConfigs.getByName("release")
        }
    }
    namespace = "com.you.veronica"
    namespace = "com.you.veronica"
  compileSdk = 34
  defaultConfig {
        applicationId = "com.you.veronica"
    minSdk = 26
    targetSdk = 34
    versionCode = 2
    versionName = "1.0"
  }
  buildTypes { getByName("debug") { isMinifyEnabled = false } }
  compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
  kotlinOptions { jvmTarget = "17" }
}
dependencies {
  implementation("androidx.core:core-ktx:1.13.1")
  implementation("androidx.appcompat:appcompat:1.7.0")
  implementation("com.google.android.material:material:1.12.0")
}
