plugins {
    libPlugins(GradlePlugin.Kapt, GradlePlugin.Hilt).forEach(::id)
}

android {

    namespace = "com.splanes.weektasks.ui"

    applyLibModuleDefaultConfig()

    applyLibFlavors()

    applyCompileOptions()

    kotlinOptions { applyKotlinOptions() }

    withFeatures(Feature.Compose)

    composeOptions { kotlinCompilerExtensionVersion = DependencyVersion.compose }
}

dependencies {
    module(name = "domain")

    androidCore()
    androidRuntime()

    hilt(isComposeNavIncluded = true)

    compose()
    lottieCompose()
    toolkitCompose()

    calendarComponent()

    test()
}