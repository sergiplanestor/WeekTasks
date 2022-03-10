object SdkConfig {
    const val min = 26
    const val compile = 31
    const val target = compile
    const val buildTools = "30.0.3"
    const val appId = "com.splanes.weektasks"
    const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

fun sdkConfig(namespace: String): com.splanes.plugins.SdkConfig =
    object : com.splanes.plugins.SdkConfig {
        override val namespace: String = namespace
        override val appId: String = "com.splanes.weektasks"
        override val min: Int = 26
        override val compile: Int = 31
        override val target: Int = compile
        override val buildTools: String = "30.0.3"
        override val instrumentationRunner: String = "androidx.test.runner.AndroidJUnitRunner"

    }