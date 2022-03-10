package com.splanes.weektasks.domain.common.date

import java.time.LocalDate
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue


fun calendar(): Calendar = Calendar.getInstance().mondayAsFirstWeekDay()

fun calendar(millis: Long): Calendar = calendar().apply { timeInMillis = millis }

fun daysTo(calendar: Calendar): Int =
    calendar().daysBetween(calendar)

fun Calendar.mondayAsFirstWeekDay(): Calendar = apply { firstDayOfWeek = Calendar.MONDAY }

fun Calendar.set(localDate: LocalDate): Calendar = apply {
    clear()
    with(localDate) { set(year, monthValue - 1, dayOfMonth) }
}

fun LocalDate.toCalendar(): Calendar = calendar().apply {
    clear()
    set(year, monthValue - 1, dayOfMonth)
}

fun Calendar.endOfWeek(): Calendar = apply {
    set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
    add(Calendar.WEEK_OF_YEAR, 1)
    add(Calendar.DAY_OF_WEEK, -1)
}

fun Calendar.isEndOfWeek(): Boolean =
    calendar(millis = this.timeInMillis).endOfWeek().timeInMillis == this.timeInMillis

fun Calendar.nextWeek(): Calendar = apply { add(Calendar.WEEK_OF_YEAR, 1) }

fun Calendar.isNextWeek(): Boolean =
    calendar().nextWeek().get(Calendar.DAY_OF_YEAR) == get(Calendar.DAY_OF_YEAR)

fun Calendar.daysBetween(calendar: Calendar): Int {
    val from = timeInMillis
    val to = calendar.timeInMillis
    return if (to < from) {
        0
    } else {
        TimeUnit.DAYS.convert(from - to, TimeUnit.MILLISECONDS).toInt()
    }.absoluteValue
}