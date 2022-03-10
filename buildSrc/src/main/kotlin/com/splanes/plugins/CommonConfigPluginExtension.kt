package com.splanes.plugins

import com.android.tools.r8.internal.P
import org.gradle.api.JavaVersion

interface SdkConfig {
    val appId: String
    val namespace: String
    val min: Int get() = 26
    val compile: Int get() = 31
    val target: Int get() = compile
    val buildTools: String get() = "30.0.3"
    val instrumentationRunner: String get() = "androidx.test.runner.AndroidJUnitRunner"

    companion object {
        fun default(appId: String, namespace: String): SdkConfig = object : SdkConfig {
            override val appId: String = appId
            override val namespace: String = namespace
        }
    }
}

interface CompileConfig {
    val jvmTarget: String get() = "11"
    val sourceCompatibility: JavaVersion get() = JavaVersion.VERSION_11
    val targetCompatibility: JavaVersion get() = JavaVersion.VERSION_11
    val isDesugarEnabled: Boolean get() = true
    val args: List<String> get() = listOf("-Xopt-in=kotlin.RequiresOptIn")

    companion object {
        fun default(): CompileConfig = object : CompileConfig {}
    }
}

enum class ModuleType {
    Application,
    Library;

    val isApp: Boolean get() = this == Application
    val isLib: Boolean get() = this == Library
}

enum class Plugin(val id: String) {
    App("com.android.application"),
    Lib("com.android.library"),
    Kotlin("kotlin-android"),
    Parcelize("kotlin-parcelize"),
    Kapt("kotlin-kapt"),
    Publish("maven-publish"),
    Hilt("dagger.hilt.android.plugin");

    companion object {
        fun pluginOf(type: ModuleType): Plugin = when (type) {
            ModuleType.Application -> App
            ModuleType.Library -> Lib
        }
    }
}

interface CommonConfigPluginExtension {

    val App get() = Plugin.App
    val Lib get() = Plugin.Lib
    val Kotlin get() = Plugin.Kotlin
    val Parcelize get() = Plugin.Parcelize
    val Kapt get() = Plugin.Kapt
    val Publish get() = Plugin.Publish
    val Hilt get() = Plugin.Hilt

    var plugins: List<Plugin>?
    var sdkConfig: SdkConfig?
    var compileConfig: CompileConfig?

    fun plugins(isApp: Boolean, vararg plugins: Plugin) {
        this.plugins = plugins.toList()
            .withDefaultPlugins(if (isApp) ModuleType.Application else ModuleType.Library)
    }

    fun List<Plugin>?.withDefaultPlugins(type: ModuleType): List<Plugin> =
        orEmpty().toMutableList().apply {
            val plugin = Plugin.pluginOf(type)
            if (!contains(plugin)) {
                add(0, plugin)
            }
            if (!contains(Plugin.Kotlin)) {
                add(1, Plugin.Kotlin)
            }
        }
}