import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.cocoapods)

    id("org.jetbrains.gradle.apple.applePlugin") version "212.4638.14-0.13.1"
}

val buildConfigGenerator by tasks.registering(Sync::class) {
    val packageName = "secrets"
    val secretProperties = readPropertiesFromFile("local.properties")
    from(
        resources.text.fromString(
            """
        |package $packageName
        |
        |object BuildConfig {
        |  const val ONBOARDING_URL = "${secretProperties.getPropertyValue("ONBOARDING_URL")}"
        |  const val IDIOM_URL = "${secretProperties.getPropertyValue("IDIOM_URL")}"
        |  const val PAYWALL_URL = "${secretProperties.getPropertyValue("PAYWALL_URL")}"
        |  const val REVENUECAT_API_KEY_ANDROID = "${secretProperties.getPropertyValue("REVENUECAT_API_KEY_ANDROID")}"
        |  const val REVENUECAT_API_KEY_IOS = "${secretProperties.getPropertyValue("REVENUECAT_API_KEY_IOS")}"
        |  const val GOOGLE_AUTH = "${secretProperties.getPropertyValue("GOOGLE_AUTH")}"
        |  const val QUIAL_LOGO = "${secretProperties.getPropertyValue("QUIAL_LOGO")}"
        |  const val SIGN_IN_GRAPHIC = "${secretProperties.getPropertyValue("SIGN_IN_GRAPHIC")}"
        |  const val TOKEN_URL = "${secretProperties.getPropertyValue("TOKEN_URL")}"
        |  const val FEEDBACK_URL = "${secretProperties.getPropertyValue("FEEDBACK_URL")}"
        |  const val APP_STORE_RATING_ANDROID = "${secretProperties.getPropertyValue("APP_STORE_RATING_ANDROID")}"
        |  const val LINKTREE_URL = "${secretProperties.getPropertyValue("LINKTREE_URL")}"
        |  const val STRIPE_URL =  "${secretProperties.getPropertyValue("STRIPE_URL")}" 
        |  const val STRIPE_CUSTOMER_PORTAL = "${secretProperties.getPropertyValue("STRIPE_CUSTOMER_PORTAL")}" 
        |  const val STRIPE_TEST_CUSTOMER_PORTAL = "${secretProperties.getPropertyValue("STRIPE_TEST_CUSTOMER_PORTAL")}"
        |  const val WEBSITE_URL = "${secretProperties.getPropertyValue("WEBSITE_URL")}"
        |  const val TEST_CREDENTIALS = "${secretProperties.getPropertyValue("TEST_CREDENTIALS")}"
        |}
        |
      """.trimMargin()
        )
    )
    {
        rename { "BuildConfig.kt" } // set the file name
        into(packageName) // change the directory to match the package
    }
    into(layout.buildDirectory.dir("generated-src/kotlin/"))
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "15.4"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }

        pod("StoreKit") {
            version = libs.versions.pods.storekit.get()
            linkOnly = true
            extraOpts += listOf("-compiler-option", "-fmodules")
        }


        pod("FirebaseCore") {
            moduleName = "FirebaseCore"
            linkOnly = true
            version = libs.versions.pods.firebase.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        pod("GoogleSignIn") {
            moduleName = "GoogleSignIn"
            linkOnly = true
            version = libs.versions.pods.signin.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.targets.native.tasks.PodGenTask>().configureEach {
        doLast {
            podfile.get().appendText("\nENV['SWIFT_VERSION'] = '5'")
        }
    }


    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            implementation(libs.ktor.client.okhttp)
            implementation(libs.core.splashscreen)

            //Firebase
            api(project.dependencies.platform(libs.firebase.bom))
            api(libs.firebase.analytics)
            api(libs.firebase.crashlytics)
            api(libs.firebase.messaging)

            // SQLDelight
        }

        commonMain {
            kotlin.srcDir(
                // convert the task to a file-provider
                buildConfigGenerator.map { it.destinationDir }
            )

            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)

                implementation(libs.kotlin.coroutines)

                implementation(libs.voyager.navigator.v110beta02)
                implementation(libs.voyager.screenmodel.v110beta02)
                implementation(libs.voyager.bottom.sheet.navigator)
                implementation(libs.voyager.tab.navigator)
                implementation(libs.voyager.transitions.v110beta02)

                implementation(libs.kmprevenuecat.purchases)
                implementation(libs.kmprevenuecat.purchases.ui)

                api(libs.koin.core)
                api(libs.koin.compose)
                api(libs.koin.compose.viewmodel)

                implementation(libs.bundles.ktor)
                implementation(libs.kotlinx.serialization.json)

                api(libs.kmpNotifier)
                implementation(libs.kmpAuth.firebase)
                implementation(libs.kmpAuth.uihelper)
                implementation(libs.kmpAuth.google)

                api(libs.datastore.preferences)
                api(libs.datastore)

                implementation(libs.kotlinx.datetime)
                implementation(libs.gitlive.firebase.analytics)
                implementation(libs.gitlive.firebase.perf)

                implementation(libs.konnectivity)

                implementation(libs.app.rating)
                implementation(libs.permissions.compose)

                implementation(libs.tts)
                implementation(libs.tts.compose)

                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor)

            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(kotlin("test-annotations-common"))
            implementation(libs.assertk)
        }

        commonTest.dependencies {
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            // implementation(libs.koin.test.junit4)
        }

        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.quial.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    lint {
        disable.add("Instantiatable")
    }

    defaultConfig {
        applicationId = "com.quial.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 46
        versionName = "1.0.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = false
    }

    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

dependencies {
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.review.ktx)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.datastore.core.android)
}


/**
 * Property File name example secrets.properties
 * two local file can be created
 * secrets.properties (can be pushed to Git) as template to show the keys,
 * secrets.local.properties with actual values (should be added to .gitignore)
 *
 * Properties file content should be like this.
 * key=value
 * key2=value2
 */
fun readPropertiesFromFile(fileName: String): Map<String, String> {
    val parts = fileName.split('.')
    val localPropertyFileName = if (parts.size >= 2) {
        val nameWithoutExtension = parts.dropLast(1).joinToString(".")
        val extension = parts.last()
        "$nameWithoutExtension.local.$extension"
    } else {
        fileName
    }
    val isLocalFileExists= File(project.rootDir,localPropertyFileName).exists()
    val fileContent = try {
        File(project.rootDir,if (isLocalFileExists) localPropertyFileName else fileName).readText()
    } catch (e: Exception) {
        e.printStackTrace()
        return emptyMap()
    }

    val properties = mutableMapOf<String, String>()

    fileContent.lines().forEach { line ->
        val keyValuePair = line.split('=')
        if (keyValuePair.size == 2) {
            properties[keyValuePair[0].trim()] = keyValuePair[1]
        }
    }
    return properties
}

/**
 * If System.env value exists it will be returned value which can be useful for CI/CD pipeline
 */
fun Map<String, String>.getPropertyValue(key: String): String? {
    val envValue = System.getenv(key)
    if (envValue.isNullOrEmpty().not()) return envValue
    return this.getOrDefault(key, null)
}

