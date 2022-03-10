plugins {
    libPlugins(
        GradlePlugin.Parcelize,
        GradlePlugin.Kapt,
        GradlePlugin.Hilt
    ).forEach(::id)
}

android {

    namespace = "com.splanes.weektasks.domain"

    applyLibModuleDefaultConfig()

    applyLibFlavors()

    applyCompileOptions()

    kotlinOptions { applyKotlinOptions() }
}

dependencies {
    androidCore()
    androidRuntime()

    hilt()
    toolkitBaseArch()

    timber(isApi = true)
}