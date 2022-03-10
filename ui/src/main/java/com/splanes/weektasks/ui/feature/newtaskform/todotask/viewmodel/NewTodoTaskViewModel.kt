package com.splanes.weektasks.ui.feature.newtaskform.todotask.viewmodel

import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timerange.TimeRange
import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timestamp.Timestamp
import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timestamp.now
import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timestamp.plus
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.utils.doOnError
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.utils.doOnSuccess
import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.utils.map
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel
import com.splanes.toolkit.compose.base_arch.logger.d
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import com.splanes.weektasks.domain.feature.task.todotask.usecase.CreateTodoTaskUseCase
import com.splanes.weektasks.ui.feature.newtaskform.todotask.contract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewTodoTaskViewModel @Inject constructor(
    private val createTodoTaskUseCase: CreateTodoTaskUseCase
) : ComponentViewModel<NewTodoTaskFormUiModel, NewTodoTaskFormEvent, NewTodoTaskFormSideEffect>() {

    override val uiStateAtStart: UiState<NewTodoTaskFormUiModel> =
        UiState.Ready(NewTodoTaskFormUiModel.onStart())

    override fun onUiEventHandled(uiEvent: NewTodoTaskFormEvent) {
        uiEvent.run {
            when (this) {
                is OnFieldChanged -> onFieldValueChanged(id = id, value = value)
                is OnSubmit -> onSubmit(
                    priority = priority,
                    title = title,
                    notes = notes,
                    deadline = deadline,
                    reminders = reminders
                )
            }
        }
    }

    private fun onFieldValueChanged(id: NewTodoTaskFormField, value: Any?) {
        updateUiModel { model ->
            when (id) {
                NewTodoTaskFormField.Title -> {
                    model.copy(title = model.title.update(value))
                }
                NewTodoTaskFormField.Notes -> {
                    model.copy(notes = model.notes.update(value))
                }
                NewTodoTaskFormField.Deadline -> {
                    model.copy(deadline = model.deadline.update(value))
                }
                NewTodoTaskFormField.Reminders -> {
                    model.copy(reminders = model.reminders.update(value))
                }
                NewTodoTaskFormField.Priority -> {
                    model.copy(priority = model.priority.update(value))
                }
            }
        }
    }

    private fun onSubmit(
        priority: Int?,
        title: String?,
        notes: String?,
        deadline: Long?,
        reminders: List<Any?>
    ) {
        val timeout = with(now()) {
            TimeRange(from = this, to = Timestamp(this.millis + 20000L))
        }
        val params = CreateTodoTaskUseCase.Params(
            priority = Task.Priority.from(priority),
            title = title.orEmpty(),
            notes = notes,
            deadline = deadline,
            /* FIXME: reminders = reminders */
        )
        createTodoTaskUseCase.launch(
            req = UseCase.Request(
                id = CreateTodoTaskUseCase.ID,
                timeout = timeout,
                params = params
            ),
            mapper = ::handleCreateTodoTaskResult
        )
    }

    private fun handleCreateTodoTaskResult(result: UseCase.Result<TodoTask?>) {
        onUiSideEffect {
            result.map(
                onSuccess = { OnTaskCreated },
                onError = { OnTaskCreationError }
            )
        }
    }
}