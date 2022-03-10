package com.splanes.plugins

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.create
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class CommonConfigPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            val params = project.extensions.create<CommonConfigPluginExtension>(PLUGIN_EXTENSION)

            params.plugins?.map { it.id }?.onEach(pluginManager::apply)

            android {
                params.sdkConfig?.let { defaultConfig(it) }
                params.compileConfig?.let { compileOptions(it) }
            }
            if (params.compileConfig?.isDesugarEnabled == true) {
                dependencies.add("implementation", DESUGAR_LIB)
            }
        }
    }

    private fun BaseExtension.defaultConfig(config: SdkConfig) {
        val isAppModule = this is BaseAppModuleExtension
        // namespace = config.namespace
        compileSdkVersion(config.compile)
        if (isAppModule) {
            buildToolsVersion(config.buildTools)
        }
        defaultConfig {
            if (isAppModule) {
                applicationId = config.appId
            }
            minSdk = config.min
            when (this@defaultConfig) {
                is BaseAppModuleExtension -> compileSdk = config.compile
                is LibraryExtension -> compileSdk = config.compile
            }
            targetSdk = config.target
            versionCode = BuildVersion.code
            versionName = BuildVersion.name
            testInstrumentationRunner = config.instrumentationRunner
            vectorDrawables { useSupportLibrary = true }
            if (!isAppModule) {
                consumerProguardFiles("consumer-rules.pro")
            }
        }
    }

    private fun BaseExtension.compileOptions(config: CompileConfig) {
        compileOptions {
            sourceCompatibility = config.sourceCompatibility
            targetCompatibility = config.targetCompatibility
            isCoreLibraryDesugaringEnabled = config.isDesugarEnabled
        }
        ktxOptions {
            jvmTarget = config.jvmTarget
            freeCompilerArgs = config.args
        }
    }

    private fun Project.android(block: BaseExtension.() -> Unit) {
        ((this as ExtensionAware).extensions.getByName("android") as BaseAppModuleExtension).apply(block)
        //(this as ExtensionAware).configure(ANDROID, block)
    }

    private fun BaseExtension.ktxOptions(block: KotlinJvmOptions.() -> Unit) {
        (this as ExtensionAware).configure(KTX_OPTIONS, block)
    }

    private fun <T> ExtensionAware.configure(name: String, block: T.() -> Unit) {
        (extensions.getByName(name) as? T)?.apply(block)
    }

    companion object {
        private const val ANDROID = "android"
        private const val KTX_OPTIONS = "kotlinOptions"
        private const val PLUGIN_EXTENSION = "CommonConfigPluginExtension"
        private const val DESUGAR_LIB = "com.android.tools:desugar_jdk_libs:1.1.5"
    }
}

