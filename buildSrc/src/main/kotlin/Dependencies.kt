import org.gradle.api.artifacts.dsl.DependencyHandler

object DependencyVersion {
    const val androidxCore = "1.7.0"
    const val androidxRuntime = "2.4.0"
    const val androidxExtensions = "2.2.0"
    const val androidxViewModel = "2.4.0"
    const val kotlinCoroutines = "1.6.0"

    const val timberLogger = "5.0.1"

    const val compose = "1.1.0-rc03"
    const val composeMaterial = "1.2.0-alpha02"
    const val composeMaterial3 = "1.0.0-alpha04"
    const val composeActivity = "1.4.0"
    const val composeNavigation = "2.5.0-alpha02"
    const val composeAccompanistNavigationAnim = "0.24.2-alpha"
    const val composeSystemUi = "0.24.1-alpha"
    const val composeMaterialIconsExt = "1.2.0-alpha05"

    const val hiltAndroid = "2.40.5"
    const val hiltViewModel = "1.0.0-alpha01"
    const val hiltCompose = "1.0.0-beta01"

    const val lottieCompose = "4.0.0"

    const val gson = "2.8.6"
    const val room = "2.4.1"

    private const val toolkit = "1.1.0"
    const val toolkitUiTheme = "$toolkit-CBA"
    const val toolkitBaseArch = "$toolkit-CBA"

    const val calendarComposeComponent = "0.3.0"
}

object Dependency {

    const val androidxCore = "androidx.core:core-ktx:${DependencyVersion.androidxCore}"
    const val androidxRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${DependencyVersion.androidxRuntime}"
    const val androidxExtensions =
        "androidx.lifecycle:lifecycle-extensions:${DependencyVersion.androidxExtensions}"
    const val androidxViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${DependencyVersion.androidxViewModel}"

    const val kotlinCoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersion.kotlinCoroutines}"
    const val kotlinCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersion.kotlinCoroutines}"

    const val timberLogger = "com.jakewharton.timber:timber:${DependencyVersion.timberLogger}"

    const val androidxComposeUi = "androidx.compose.ui:ui:${DependencyVersion.compose}"
    const val androidxComposeMaterial =
        "androidx.compose.material:material:${DependencyVersion.composeMaterial}"
    const val androidxComposeMaterial3 =
        "androidx.compose.material3:material3:${DependencyVersion.composeMaterial3}"
    const val androidxComposeUiPreview =
        "androidx.compose.ui:ui-tooling-preview:${DependencyVersion.compose}"
    const val androidxComposeActivity =
        "androidx.activity:activity-compose:${DependencyVersion.composeActivity}"
    const val androidxComposeNavigation =
        "androidx.navigation:navigation-compose:${DependencyVersion.composeNavigation}"
    const val composeAccompanistNavigationAnim =
        "com.google.accompanist:accompanist-navigation-animation:${DependencyVersion.composeAccompanistNavigationAnim}"
    const val androidxComposeViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${DependencyVersion.composeNavigation}"
    const val composeSystemUi =
        "com.google.accompanist:accompanist-systemuicontroller:${DependencyVersion.composeSystemUi}"
    const val composeMaterialIconsExt =
        "androidx.compose.material:material-icons-extended:${DependencyVersion.composeMaterialIconsExt}"

    const val gson = "com.google.code.gson:gson:${DependencyVersion.gson}"
    const val roomRuntime = "androidx.room:room-runtime:${DependencyVersion.room}"
    const val roomCommon = "androidx.room:room-common:${DependencyVersion.room}"
    const val roomKtx = "androidx.room:room-ktx:${DependencyVersion.room}"
    const val roomCompiler = "androidx.room:room-compiler:${DependencyVersion.room}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${DependencyVersion.hiltAndroid}"
    const val hiltCompiler =
        "com.google.dagger:hilt-android-compiler:${DependencyVersion.hiltAndroid}"
    const val hiltViewModel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${DependencyVersion.hiltViewModel}"
    const val hiltComposeNavigation =
        "androidx.hilt:hilt-navigation-compose:${DependencyVersion.hiltCompose}"

    const val lottieCompose = "com.airbnb.android:lottie-compose:${DependencyVersion.lottieCompose}"

    private const val toolkit = "com.github.sergiplanestor.toolkit"
    const val toolkitUiTheme = "${toolkit}:theme:${DependencyVersion.toolkitUiTheme}"
    const val toolkitBaseArch = "${toolkit}:base-arch:${DependencyVersion.toolkitBaseArch}"

    const val calendarComposeComponent =
        "io.github.boguszpawlowski.composecalendar:composecalendar:${DependencyVersion.calendarComposeComponent}"
}

fun DependencyHandler.androidCore() {
    impl(Dependency.androidxCore)
}

fun DependencyHandler.androidRuntime() {
    impl(Dependency.androidxRuntime)
}

fun DependencyHandler.androidExtensions() {
    impl(Dependency.androidxExtensions)
}

fun DependencyHandler.androidViewModel() {
    impl(Dependency.androidxViewModel)
}

fun DependencyHandler.ktxCoroutines() {
    impl(Dependency.kotlinCoroutinesCore)
    impl(Dependency.kotlinCoroutinesAndroid)
}

fun DependencyHandler.timber(isApi: Boolean = false) {
    impl(Dependency.timberLogger, isApi = isApi)
}

fun DependencyHandler.room() {
    impl(Dependency.roomKtx)
    impl(Dependency.roomRuntime)
    impl(Dependency.roomCommon)
    impl(Dependency.roomCompiler, isKapt = true)
}

fun DependencyHandler.toolkitUiTheme() {
    impl(Dependency.toolkitUiTheme)
}

fun DependencyHandler.toolkitBaseArch() {
    impl(Dependency.toolkitBaseArch)
}

fun DependencyHandler.toolkitCompose() {
    toolkitBaseArch()
    toolkitUiTheme()
}

fun DependencyHandler.lottieCompose() {
    impl(Dependency.lottieCompose)
}

fun DependencyHandler.composeBase(isMaterial3: Boolean = true) {
    impl(Dependency.androidxComposeUi)
    impl(Dependency.androidxComposeActivity)
    impl(Dependency.androidxComposeUiPreview)
    composeMaterial()
    if (isMaterial3) composeMaterial3()
    composeMaterialIconsExt()

    test(Test.androidxComposeJUnit, isUiTest = true)

    apply(Test.androidxComposeTooling, ImplType.DebugImpl)
}

fun DependencyHandler.composeMaterial() {
    impl(Dependency.androidxComposeMaterial)
}

fun DependencyHandler.composeMaterial3() {
    impl(Dependency.androidxComposeMaterial3)
}

fun DependencyHandler.composeMaterialIconsExt() {
    impl(Dependency.composeMaterialIconsExt)
}

fun DependencyHandler.composeNavigation(isNavAnim: Boolean = true) {
    impl(Dependency.androidxComposeNavigation)
    if (isNavAnim) composeNavigationAnim()
}

fun DependencyHandler.composeNavigationAnim() {
    impl(Dependency.composeAccompanistNavigationAnim)
}

fun DependencyHandler.composeViewModel() {
    impl(Dependency.androidxComposeViewModel)
}

fun DependencyHandler.composeSystemUi() {
    impl(Dependency.composeSystemUi)
}

fun DependencyHandler.compose() {
    composeBase()
    composeNavigation()
    composeViewModel()
    composeSystemUi()
}

fun DependencyHandler.gson() {
    impl(Dependency.gson)
}

fun DependencyHandler.hilt(isComposeNavIncluded: Boolean = false) {
    impl(Dependency.hiltAndroid)
    impl(Dependency.hiltCompiler, isKapt = true)
    impl(Dependency.hiltViewModel)
    if (isComposeNavIncluded) impl(Dependency.hiltComposeNavigation)
    test(Test.hiltAndroidTesting)
    test(Test.hiltAndroidTesting, isUiTest = true)
    apply(Test.hiltCompilerTesting, ImplType.Other("kaptTest"))
    apply(Test.hiltCompilerTesting, ImplType.Other("kaptAndroidTest"))
}

fun DependencyHandler.test() {
    test(Test.junit)
    test(Test.androidxJUnit, isUiTest = true)
    test(Test.androidxEspressoCore, isUiTest = true)
}

fun DependencyHandler.calendarComponent() {
    impl(Dependency.calendarComposeComponent)
}

object TestVersion {
    const val junit = "4.13.2"
    const val androidxJUnit = "1.1.3"
    const val androidxEspressoCore = "3.4.0"
    const val androidxComposeJUnit = DependencyVersion.compose
    const val androidxComposeTooling = DependencyVersion.compose
    const val hiltAndroid = DependencyVersion.hiltAndroid
}

object Test {
    const val junit = "junit:junit:${TestVersion.junit}"
    const val androidxJUnit = "androidx.test.ext:junit:${TestVersion.androidxJUnit}"
    const val androidxEspressoCore =
        "androidx.test.espresso:espresso-core:${TestVersion.androidxEspressoCore}"
    const val androidxComposeJUnit =
        "androidx.compose.ui:ui-test-junit4:${TestVersion.androidxComposeJUnit}"
    const val androidxComposeTooling =
        "androidx.compose.ui:ui-tooling:${TestVersion.androidxComposeTooling}"
    const val hiltAndroidTesting =
        "com.google.dagger:hilt-android-testing:${TestVersion.hiltAndroid}"
    const val hiltCompilerTesting =
        "com.google.dagger:hilt-android-compiler:${TestVersion.hiltAndroid}"
}