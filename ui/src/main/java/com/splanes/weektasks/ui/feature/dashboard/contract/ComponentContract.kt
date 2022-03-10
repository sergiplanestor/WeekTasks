package com.splanes.weektasks.ui.feature.dashboard.contract

import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiEvent
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiSideEffect
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask

data class DashboardUiModel(
    val todoTasks: List<TodoTask>,
    // val dailyTasks: List<>,
    val isEditModeEnabled: Boolean = false
) : UiModel

sealed class DashboardEvent : UiEvent {
    sealed class NewTask: DashboardEvent()
    object NewTodoTask : NewTask()
    object NewDailyTask : NewTask()
    object RemoveTasksSelected : DashboardEvent()
    object LoadTasks : DashboardEvent()
}

sealed class DashboardSideEffect : UiSideEffect {
    data class RedirectToNewTaskForm(val isTodoTask: Boolean) : DashboardSideEffect()
}