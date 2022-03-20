package com.splanes.weektasks.utils.kotlinext.scope

fun <T> T.applyIf(cond: Boolean, default: T = this, block: T.() -> T): T = if (cond) block() else default