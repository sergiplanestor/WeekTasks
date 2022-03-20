package com.splanes.weektasks.ui.feature.sort.model

sealed class TaskSort(open val id: Int, open val desc: Boolean) {
    data class Title(override val desc: Boolean = false) :
        TaskSort(id = SORT_ID_BY_TITLE, desc = desc)

    data class Priority(override val desc: Boolean = false) :
        TaskSort(id = SORT_ID_BY_PRIORITY, desc = desc)

    companion object {
        private const val SORT_ID_BY_TITLE = 0
        private const val SORT_ID_BY_PRIORITY = 1
    }
}
