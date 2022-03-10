package com.splanes.weektasks.ui.feature.newtaskform.todotask.contract

import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiEvent
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiSideEffect
import com.splanes.weektasks.domain.feature.task.model.Reminder
import com.splanes.weektasks.ui.feature.newtaskform.common.model.FormField
import com.splanes.weektasks.ui.feature.newtaskform.common.model.FormUiModel
import com.splanes.weektasks.ui.feature.newtaskform.common.model.FormValidator
import com.splanes.weektasks.ui.R.string as Strings

sealed class NewTodoTaskFormField: FormField {
    object Title: NewTodoTaskFormField()
    object Notes: NewTodoTaskFormField()
    object Deadline: NewTodoTaskFormField()
    object Reminders: NewTodoTaskFormField()
    object Priority: NewTodoTaskFormField()
}

data class NewTodoTaskFormUiModel(
    val title: FormUiModel<NewTodoTaskFormField>,
    val notes: FormUiModel<NewTodoTaskFormField>,
    val deadline: FormUiModel<NewTodoTaskFormField>,
    val reminders: FormUiModel<NewTodoTaskFormField>,
    val priority: FormUiModel<NewTodoTaskFormField>
): UiModel {

    companion object {
        fun onStart(): NewTodoTaskFormUiModel =
            NewTodoTaskFormUiModel(
                title = FormUiModel.Idle(
                    id = NewTodoTaskFormField.Title,
                    validators = listOf(FormValidator.NotEmpty()),
                    placeholder = Strings.task_form_title_field_placeholder
                ),
                notes = FormUiModel.Idle(
                    id = NewTodoTaskFormField.Notes,
                    placeholder = Strings.task_form_notes_field_placeholder
                ),
                deadline = FormUiModel.Idle(
                    id = NewTodoTaskFormField.Deadline,
                    placeholder = Strings.task_form_deadline_field_placeholder_empty
                ),
                reminders = FormUiModel.Idle(
                    id = NewTodoTaskFormField.Reminders,
                    placeholder = Strings.task_form_reminders_field_placeholder
                ),
                priority = FormUiModel.Idle(
                    id = NewTodoTaskFormField.Priority,
                    validators = listOf(FormValidator.NotEmpty()),
                    placeholder = Strings.task_form_priority_field_placeholder
                )
            )
    }
}


sealed class NewTodoTaskFormEvent : UiEvent

data class OnFieldChanged(
    val id: NewTodoTaskFormField,
    val value: Any?
) : NewTodoTaskFormEvent()

data class OnSubmit(
    val priority: Int?,
    val title: String?,
    val notes: String?,
    val deadline: Long?,
    val reminders: List<Any?> = emptyList()
) : NewTodoTaskFormEvent()


sealed class NewTodoTaskFormSideEffect : UiSideEffect

object OnTaskCreated : NewTodoTaskFormSideEffect()
object OnTaskCreationError : NewTodoTaskFormSideEffect()