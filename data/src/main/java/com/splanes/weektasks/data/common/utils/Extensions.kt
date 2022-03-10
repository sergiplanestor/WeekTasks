package com.splanes.weektasks.data.common.utils

fun Int.toBoolean(): Boolean = this == 1

fun Boolean.toInt(): Int = if(this) 1 else 0

fun Long?.orDefault(): Long = this ?: 0L