package com.splanes.weektasks.ui.feature.sort.model

import com.splanes.weektasks.domain.feature.task.model.Task

sealed class TaskSort(open val id: Int, open val desc: Boolean) {
    data class Title(override val desc: Boolean = false) :
        TaskSort(id = SORT_ID_BY_TITLE, desc = desc)

    data class Priority(override val desc: Boolean = false) :
        TaskSort(id = SORT_ID_BY_PRIORITY, desc = desc)

    fun clone(desc: Boolean): TaskSort =
        when (this) {
            is Priority -> this.copy(desc = desc)
            is Title ->  this.copy(desc = desc)
        }

    companion object {
        private const val SORT_ID_BY_TITLE = 0
        private const val SORT_ID_BY_PRIORITY = 1
    }
}

fun <T : Task> List<T>.sort(criteria: TaskSort): List<T> {

    val comp: (T) -> Comparable<*>? =  {
        when (criteria) {
            is TaskSort.Priority -> it.priority.weight
            is TaskSort.Title -> it.title
        }
    }
    return sortedWith(if(criteria.desc) compareByDescending(comp) else compareBy(comp))
}