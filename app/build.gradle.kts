plugins {
    appPlugins(GradlePlugin.Kapt, GradlePlugin.Hilt).forEach(::id)
}

android {

    namespace = "com.splanes.weektasks"

    applyAppDefaultConfig()

    applyCompileOptions()

    kotlinOptions { applyKotlinOptions() }

    applyAppFlavors()

    withFeatures(Feature.Compose)

    composeOptions { kotlinCompilerExtensionVersion = DependencyVersion.compose }
}

dependencies {

    module(name = "data")
    module(name = "domain")
    module(name = "ui")

    androidCore()
    androidRuntime()
    hilt()

    compose()

    test()
}