package com.splanes.weektasks.domain.common.date

import java.util.*

fun now(): Long = System.currentTimeMillis()

fun weekOfYear(): Int = Calendar.getInstance().apply { timeInMillis = now() }.weekYear