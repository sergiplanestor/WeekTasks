plugins {
    libPlugins(
        GradlePlugin.Kapt,
        GradlePlugin.Hilt
    ).forEach(::id)
}

android {

    namespace = "com.splanes.weektasks.data"

    applyLibModuleDefaultConfig()

    applyLibFlavors()

    applyCompileOptions()

    kotlinOptions { applyKotlinOptions() }

}

dependencies {

    module(name = "domain")

    androidCore()
    androidRuntime()

    hilt()
    room()

    gson()
}