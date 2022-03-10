package com.splanes.weektasks.ui.common.utils

import com.splanes.weektasks.domain.common.date.calendar
import java.text.SimpleDateFormat
import java.util.*

const val DD_MM_YY = "dd/MM/yy"

fun formatCalendar(
    pattern: String = DD_MM_YY,
    locale: Locale = Locale.getDefault(),
    block: Calendar.() -> Unit
): String = calendar().apply(block).format(pattern, locale)

fun Calendar.format(pattern: String = DD_MM_YY, locale: Locale = Locale.getDefault()): String =
    SimpleDateFormat(pattern, locale).format(timeInMillis)