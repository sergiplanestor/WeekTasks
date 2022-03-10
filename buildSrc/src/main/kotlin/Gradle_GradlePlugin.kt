import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Action
import org.gradle.kotlin.dsl.configure

sealed class GradlePlugin(open val value: String) {
    object App : GradlePlugin("com.android.application")
    object Lib : GradlePlugin("com.android.library")
    object Kotlin : GradlePlugin("kotlin-android")
    object Parcelize : GradlePlugin("kotlin-parcelize")
    object Kapt : GradlePlugin("kotlin-kapt")
    object Publish : GradlePlugin("maven-publish")
    object Hilt : GradlePlugin("dagger.hilt.android.plugin")
    data class Other(override val value: String) : GradlePlugin(value)

    companion object {
        fun getIds(isAppModule: Boolean, vararg plugins: GradlePlugin): List<String> =
            plugins.toMutableList().apply {
                val moduleTypePlugin = if (isAppModule) App else Lib
                if (!contains(moduleTypePlugin)) {
                    add(0, moduleTypePlugin)
                }
                if (!contains(Kotlin)) {
                    add(1, Kotlin)
                }
            }.map { it.value }

        fun getAppIds(vararg plugins: GradlePlugin) =
            getIds(isAppModule = true, *plugins)

        fun getLibIds(vararg plugins: GradlePlugin) =
            getIds(isAppModule = false, *plugins)
    }
}

fun appPlugins(vararg plugins: GradlePlugin): List<String> = GradlePlugin.getAppIds(*plugins)
fun libPlugins(vararg plugins: GradlePlugin): List<String> = GradlePlugin.getLibIds(*plugins)

fun org.gradle.api.Project.commonConfig(configure: com.splanes.plugins.CommonConfigPluginExtension.() -> Unit): Unit =
    configure(configure)