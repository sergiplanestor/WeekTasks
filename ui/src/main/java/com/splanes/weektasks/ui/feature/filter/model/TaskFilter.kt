package com.splanes.weektasks.ui.feature.filter.model

import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.domain.feature.task.model.hasDeadline
import com.splanes.weektasks.ui.common.utils.list.mutate
import com.splanes.weektasks.ui.common.utils.list.removeAll

sealed class TaskFilter(open val id: Int) {

    object OnlyPending : TaskFilter(id = FILTER_ID_ONLY_PENDING)

    object HasDeadline : TaskFilter(id = FILTER_ID_HAS_DEADLINE)
    
    object HasReminders : TaskFilter(id = FILTER_ID_HAS_REMINDERS)
    
    data class OverPriority(
        val value: Task.Priority,
        val isInclusive: Boolean = true
    ) : TaskFilter(id = FILTER_ID_OVER_PRIORITY)

    sealed class Date(override val id: Int, open val from: Long, open val to: Long) : TaskFilter(id)

    data class Before(override val from: Long, override val to: Long) :
        Date(id = FILTER_ID_DATE_BEFORE, from = from, to = to)

    data class Between(override val from: Long, override val to: Long) :
        Date(id = FILTER_ID_DATE_BETWEEN, from = from, to = to)

    data class After(override val from: Long, override val to: Long) :
        Date(id = FILTER_ID_DATE_AFTER, from = from, to = to)

    data class BeforeDeadline(override val from: Long, override val to: Long) :
        Date(id = FILTER_ID_DATE_BEFORE_DEADLINE, from = from, to = to)

    companion object {
        private const val FILTER_ID_ONLY_PENDING = 0
        private const val FILTER_ID_HAS_DEADLINE = 1
        private const val FILTER_ID_HAS_REMINDERS = 2
        private const val FILTER_ID_OVER_PRIORITY = 3
        private const val FILTER_ID_DATE_BEFORE = 4
        private const val FILTER_ID_DATE_BETWEEN = 5
        private const val FILTER_ID_DATE_AFTER = 6
        private const val FILTER_ID_DATE_BEFORE_DEADLINE = 7
    }
}

fun <T : Task> T.matches(filter: TaskFilter): Boolean =
    when (filter) {
        is TaskFilter.Date -> {
            TODO()
        }
        TaskFilter.HasDeadline ->
            hasDeadline()
        TaskFilter.HasReminders ->
            reminders.isNotEmpty()
        TaskFilter.OnlyPending ->
            !isDone
        is TaskFilter.OverPriority ->
            priority.weight > filter.value.weight || (priority.weight == filter.value.weight && filter.isInclusive)
    }

fun <T : Task> T.matches(filters: List<TaskFilter>): Boolean =
    filters.any(::matches)

fun <T : Task> List<T>.filter(filter: TaskFilter): List<T> =
    filter(listOf(filter))

fun <T : Task> List<T>.filter(filters: List<TaskFilter>): List<T> = mutate {
    removeAll { task -> task.matches(filters) }
}
