package com.splanes.weektasks.ui.common.utils.debug

import com.splanes.weektasks.ui.BuildConfig

fun isDebug(): Boolean = BuildConfig.DEBUG

fun <T> onDebug(block: () -> T): T? = if (isDebug()) block() else null