package com.splanes.weektasks.ui.feature.dashboard.viewmodel

import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.utils.doOnSuccess
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import com.splanes.weektasks.domain.feature.task.todotask.usecase.DataPopulateDebugUseCase
import com.splanes.weektasks.domain.feature.task.todotask.usecase.DeleteAllDebugUseCase
import com.splanes.weektasks.domain.feature.task.todotask.usecase.FetchTodoTasksUseCase
import com.splanes.weektasks.domain.feature.task.todotask.usecase.UpdateTodoTaskUseCase
import com.splanes.weektasks.ui.common.utils.list.ListConflictResolution
import com.splanes.weektasks.ui.common.utils.list.addUnique
import com.splanes.weektasks.ui.common.utils.list.removeAll
import com.splanes.weektasks.ui.common.utils.list.replace
import com.splanes.weektasks.ui.common.utils.viewmodel.timeout
import com.splanes.weektasks.ui.feature.dashboard.contract.AutoPopulate
import com.splanes.weektasks.ui.feature.dashboard.contract.CloseModalEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardModalEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardSideEffect
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardUiModel
import com.splanes.weektasks.ui.feature.dashboard.contract.LoadTasks
import com.splanes.weektasks.ui.feature.dashboard.contract.RemoveDatabase
import com.splanes.weektasks.ui.feature.dashboard.contract.RemoveFilters
import com.splanes.weektasks.ui.feature.dashboard.contract.RemoveTasksSelected
import com.splanes.weektasks.ui.feature.dashboard.contract.UpdateFilters
import com.splanes.weektasks.ui.feature.dashboard.contract.UpdateSort
import com.splanes.weektasks.ui.feature.dashboard.contract.UpdateTask
import com.splanes.weektasks.ui.feature.filter.model.TaskFilter
import com.splanes.weektasks.ui.feature.filter.model.filter
import com.splanes.weektasks.ui.feature.sort.model.TaskSort
import com.splanes.weektasks.ui.feature.sort.model.sort
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val fetchTodoTasksUseCase: FetchTodoTasksUseCase,
    private val updateTodoTasksUseCase: UpdateTodoTaskUseCase,
    private val dataPopulateDebugUseCase: DataPopulateDebugUseCase,
    private val deleteAllDebugUseCase: DeleteAllDebugUseCase,
) : ComponentViewModel<DashboardUiModel, DashboardEvent, DashboardSideEffect>() {

    init {
        updateUiState { UiState.Ready(DashboardUiModel.onStart()) }
        onLoadTasks()
    }

    override fun onUiEventHandled(uiEvent: DashboardEvent) {
        when (uiEvent) {
            RemoveTasksSelected -> onRemoveSelectedTasks()
            LoadTasks -> onLoadTasks()
            is UpdateTask -> onUpdateTask(uiEvent.task)
            is UpdateSort -> onUpdateSort(uiEvent.sort)
            is UpdateFilters -> onUpdateFilters(uiEvent.filter, uiEvent.isApplied)
            is DashboardModalEvent -> onModalStateChanged(uiEvent)
            RemoveFilters -> onRemoveFilters()
            AutoPopulate -> onAutoPopulate()
            RemoveDatabase -> onRemoveDatabase()
        }
    }

    private fun onModalStateChanged(modal: DashboardModalEvent) =
        updateUiModel { from -> from.copy(modal = modal.takeUnless { it is CloseModalEvent }) }

    private fun onRemoveSelectedTasks() {
        /* TODO */
    }

    private fun onAutoPopulate() {
        dataPopulateDebugUseCase.launch(
            req = UseCase.Request(
                DataPopulateDebugUseCase.ID,
                Unit,
                timeout = timeout
            ),
            mapper = { doOnSuccess { onLoadTasks() } }
        )
    }

    private fun onRemoveDatabase() {
        deleteAllDebugUseCase.launch(
            req = UseCase.Request(
                DeleteAllDebugUseCase.ID,
                Unit,
                timeout = timeout
            ),
            mapper = { doOnSuccess { onLoadTasks() } }
        )
    }

    private fun onLoadTasks() {
        fetchTodoTasksUseCase.launch(
            req = UseCase.Request(
                FetchTodoTasksUseCase.ID,
                FetchTodoTasksUseCase.Params,
                timeout = timeout
            ),
            mapper = ::handleLoadedTasksResult
        )
    }

    private fun handleLoadedTasksResult(result: UseCase.Result<List<TodoTask>>) {
        result.doOnSuccess { tasks -> updateTodoTasks { tasks } }
    }

    private fun onUpdateTask(task: Task) {
        if (task is TodoTask) {
            updateTodoTasksUseCase.launch(
                req = UseCase.Request(
                    UpdateTodoTaskUseCase.ID,
                    UpdateTodoTaskUseCase.Params(task),
                    timeout = timeout
                ),
                mapper = ::handleUpdateTaskResult
            )
        }
    }

    private fun handleUpdateTaskResult(result: UseCase.Result<TodoTask>) {
        result.doOnSuccess { task ->
            updateTodoTasks {
                replace(
                    matcher = { it.id == task.id },
                    transformation = { task }
                )
            }
        }
    }

    private fun updateTodoTasks(update: List<TodoTask>.() -> List<TodoTask>) =
        updateUiModel { from ->
            with(from) {
                copy(
                    todoTasks = todoTasks
                        .update()
                        .filter(filters)
                        .sort(sort)
                )
            }
        }

    private fun onUpdateFilters(filter: TaskFilter, isApplied: Boolean) {
        updateAppliedFilters {
            when {
                !isApplied -> removeAll { it.id == filter.id }
                isApplied -> addUnique(
                    item = filter,
                    onConflict = ListConflictResolution.Replace,
                    checker = { it.id == filter.id }
                )
                else -> this
            }
        }
    }

    private fun onUpdateSort(sort: TaskSort?) {
        sort?.run {
            updateUiModel { model -> model.copy(sort = this) }
            onLoadTasks()
        }
    }

    private fun onRemoveFilters() = updateAppliedFilters { emptyList() }

    private fun updateAppliedFilters(update: List<TaskFilter>.() -> List<TaskFilter>) {
        updateUiModel { model -> model.copy(filters = model.filters.update()) }
        onLoadTasks()
    }
}