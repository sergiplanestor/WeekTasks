package com.splanes.weektasks.ui.feature.dashboard.contract

import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiEvent
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiSideEffect
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import com.splanes.weektasks.ui.feature.filter.model.TaskFilter
import com.splanes.weektasks.ui.feature.sort.model.TaskSort

data class DashboardUiModel(
    val todoTasks: List<TodoTask>,
    // val dailyTasks: List<>,
    val filters: List<TaskFilter>,
    val sort: TaskSort,
    val modal: DashboardModalEvent? = null,
    val isEditModeEnabled: Boolean = false
) : UiModel {
    companion object {
        fun onStart(): DashboardUiModel = DashboardUiModel(
            todoTasks = emptyList(),
            filters = emptyList(),
            sort = TaskSort.Priority()
        )
    }
}
// Events
sealed class DashboardEvent : UiEvent

// Load tasks
object LoadTasks : DashboardEvent()

// Debug
object AutoPopulate : DashboardEvent()
object RemoveDatabase : DashboardEvent()

// On Edit mode events
object RemoveTasksSelected : DashboardEvent()

data class UpdateTask(val task: Task) : DashboardEvent()
data class UpdateFilters(val filter: TaskFilter, val isApplied: Boolean) : DashboardEvent()
data class UpdateSort(val sort: TaskSort?) : DashboardEvent()
object RemoveFilters : DashboardEvent()

sealed class DashboardModalEvent : DashboardEvent()

object CloseModalEvent : DashboardModalEvent()
sealed class OpenModalEvent : DashboardModalEvent()

data class OpenNewTaskModal(val taskType: Task.Type) : OpenModalEvent()
data class OpenTaskFiltersModal(val type: Task.Type, val applied: List<TaskFilter>) : OpenModalEvent()
data class OpenTaskSortModal(val type: Task.Type, val applied: TaskSort?) : OpenModalEvent()

object DashboardSideEffect : UiSideEffect