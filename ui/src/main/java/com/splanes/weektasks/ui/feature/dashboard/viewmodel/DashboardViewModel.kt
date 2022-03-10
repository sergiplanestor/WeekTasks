package com.splanes.weektasks.ui.feature.dashboard.viewmodel

import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timerange.TimeRange
import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timestamp.Timestamp
import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timestamp.now
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.utils.doOnSuccess
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.utils.map
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import com.splanes.weektasks.domain.feature.task.todotask.usecase.FetchTodoTasksUseCase
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardSideEffect
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardUiModel
import com.splanes.weektasks.ui.feature.newtaskform.todotask.contract.OnTaskCreated
import com.splanes.weektasks.ui.feature.newtaskform.todotask.contract.OnTaskCreationError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val fetchTodoTasksUseCase: FetchTodoTasksUseCase
) : ComponentViewModel<DashboardUiModel, DashboardEvent, DashboardSideEffect>() {

    init {
        updateUiState { UiState.Ready(DashboardUiModel(todoTasks = emptyList())) }
        onLoadTasks()
    }

    override fun onUiEventHandled(uiEvent: DashboardEvent) {
        when (uiEvent) {
            DashboardEvent.NewTodoTask -> onNewTask(isTodoTask = true)
            DashboardEvent.NewDailyTask -> onNewTask(isTodoTask = false)
            DashboardEvent.RemoveTasksSelected -> onRemoveSelectedTasks()
            DashboardEvent.LoadTasks -> onLoadTasks()
        }
    }

    private fun onNewTask(isTodoTask: Boolean) =
        onUiSideEffect { DashboardSideEffect.RedirectToNewTaskForm(isTodoTask) }

    private fun onRemoveSelectedTasks() {
        /* TODO */
    }

    private fun onLoadTasks() {
        val timeout = with(now()) {
            TimeRange(from = this, to = Timestamp(this.millis + 20000L))
        }
        fetchTodoTasksUseCase.launch(
            req = UseCase.Request(FetchTodoTasksUseCase.ID, FetchTodoTasksUseCase.Params, timeout = timeout),
            mapper = ::handleLoadedTasksResult
        )
    }

    private fun handleLoadedTasksResult(result: UseCase.Result<List<TodoTask>>) {
        result.doOnSuccess { tasks -> updateUiModel { from -> from.copy(todoTasks = tasks) } }
    }
}