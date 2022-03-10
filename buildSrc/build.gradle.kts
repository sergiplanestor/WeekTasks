plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

gradlePlugin {
    plugins {
        register("common-config") {
            id = "common-config"
            implementationClass = "com.splanes.plugins.CommonConfigPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.2")
    implementation(kotlin("gradle-plugin", version = "1.6.10"))
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
}