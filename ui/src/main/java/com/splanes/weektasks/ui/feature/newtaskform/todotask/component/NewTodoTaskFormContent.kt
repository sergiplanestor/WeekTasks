package com.splanes.weektasks.ui.feature.newtaskform.todotask.component

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.theme.utils.accessors.*
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.ui.common.priority.icon
import com.splanes.weektasks.ui.common.priority.name
import com.splanes.weektasks.ui.common.utils.Strings
import com.splanes.weektasks.ui.common.utils.painter
import com.splanes.weektasks.ui.common.utils.string
import com.splanes.weektasks.ui.feature.newtaskform.common.component.*
import com.splanes.weektasks.ui.feature.newtaskform.common.model.FormUiModel
import com.splanes.weektasks.ui.feature.newtaskform.todotask.contract.*
import com.splanes.weektasks.ui.feature.newtaskform.todotask.viewmodel.NewTodoTaskViewModel

@Composable
fun ColumnScope.NewTodoTaskFormContent(viewModel: NewTodoTaskViewModel) {

    val uiState = viewModel.uiState.value

    if (uiState is UiState.Ready) {
        with(uiState.data) {

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                formUiModel = title,
                onUiEvent = viewModel::onUiEvent
            )
            Spacer(modifier = Modifier.height(ComponentPaddings.mediumSmall))
            TextField(
                formUiModel = notes,
                onUiEvent = viewModel::onUiEvent
            )
            Spacer(modifier = Modifier.height(ComponentPaddings.medium))

            TaskPriorityPicker(formUiModel = priority, onUiEvent = viewModel::onUiEvent)

            Spacer(modifier = Modifier.height(ComponentPaddings.medium))

            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = isFormValid(),
                onClick = { viewModel.onUiEvent(toOnSubmitEvent()) },
                border = BorderStroke(width = 1.dp, Colors.primary),
            ) {
                Text(
                    text = string { Strings.button_create },
                    style = Typographies.Title.medium,
                    color = Colors.primary
                )
            }
        }
    }
}

@Composable
fun TextField(
    formUiModel: FormUiModel<NewTodoTaskFormField>,
    onUiEvent: (NewTodoTaskFormEvent) -> Unit
) {
    val isError = formUiModel is FormUiModel.Error
    val value = formUiModel.value?.toString()
    var isFieldFocused by remember { mutableStateOf(false) }
    var isTitleVisible by remember { mutableStateOf(!value.isNullOrBlank()) }
    var isClearVisible by remember { mutableStateOf(value?.isNotBlank() == true) }
    val onFocusChanged: (FocusState) -> Unit = { focusState ->
        isFieldFocused = focusState.isFocused
        isClearVisible = isFieldFocused && (value?.isNotBlank() == true)
    }
    val onTextChanged: (String) -> Unit = { text ->
        text.isNotBlank().run {
            isTitleVisible = this
            isClearVisible = this
        }
        onUiEvent(OnFieldChanged(id = formUiModel.id, value = text))
    }

    Column(modifier = Modifier.padding(horizontal = ViewportPaddings.medium)) {

        TextFieldTitle(
            isVisible = isTitleVisible,
            isFocused = isFieldFocused,
            isError = isError,
            title = formUiModel.placeholder
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged(onFocusChanged),
            colors = textFieldColors(),
            value = formUiModel.value?.toString().orEmpty(),
            placeholder = {
                formUiModel.placeholder?.let {
                    Text(
                        text = string { it },
                        style = Typographies.Body.large
                    )
                }
            },
            onValueChange = onTextChanged,
            isError = isError,
            trailingIcon = {
                TextFieldClearButton(
                    isVisible = isClearVisible,
                    isError = isError,
                    onClick = { onTextChanged("") }
                )
            }
        )

        TextFieldError(
            isError = isError,
            isFocused = isFieldFocused,
            error = (formUiModel as? FormUiModel.Error<NewTodoTaskFormField>)?.errorMessage
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskPriorityPicker(
    formUiModel: FormUiModel<NewTodoTaskFormField>,
    onUiEvent: (NewTodoTaskFormEvent) -> Unit
) {
    val selected = Task.Priority.from(formUiModel.value as? Int)

    Column(modifier = Modifier.padding(horizontal = ViewportPaddings.medium)) {
        TextFieldTitle(
            isVisible = true,
            isFocused = false,
            isError = false,
            title = formUiModel.placeholder
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = ComponentPaddings.medium)
        ) {
            itemsIndexed(Task.Priority.values()) { index, priority ->
                if (index == 0) Spacer(modifier = Modifier.width(ComponentPaddings.mediumSmall))
                PriorityPill(
                    priority = priority,
                    isSelected = priority == selected,
                    onClick = { onUiEvent(OnFieldChanged(formUiModel.id, priority.weight)) }
                )
                Spacer(modifier = Modifier.width(if (index == Task.Priority.values().lastIndex) ComponentPaddings.mediumSmall else ComponentPaddings.small))
            }
        }
    }
}

@Composable
fun PriorityPill(priority: Task.Priority, isSelected: Boolean, onClick: (Task.Priority) -> Unit) {
    Surface(
        shape = Shapes.large,
        color = if (isSelected) Colors.tertiaryContainer.composite(
            Colors.surface,
            .45
        ) else Colors.surface,
        onClick = { onClick(priority) }
    ) {
        Row(
            modifier = Modifier.padding(
                all = ComponentPaddings.small
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(size = 26.dp),
                painter = painter { priority.icon() },
                tint = if (isSelected) {
                    Colors.tertiary
                } else {
                    Colors.onSurface.alpha(.55)
                },
                contentDescription = null
            )
            Animated(
                isVisible = isSelected,
                duration = 250,
                enter = expandHorizontally(animationSpec = tween(durationMillis = 500)),
                exit = shrinkHorizontally(animationSpec = tween(durationMillis = 500)),
            ) {
                Text(
                    modifier = Modifier.padding(start = ComponentPaddings.smallTiny),
                    text = string { priority.name() },
                    style = Typographies.Title.medium.copy(fontSize = 18.sp),
                    color = if (isSelected) Colors.onTertiaryContainer else Colors.onSurface
                )
            }
        }
    }
}

private fun NewTodoTaskFormUiModel.isFormValid(): Boolean =
    title.validators.all { it.isValid(title.value) } &&
            notes.validators.all { it.isValid(notes.value) } &&
            priority.validators.all { it.isValid(priority.value) }

private fun NewTodoTaskFormUiModel.toOnSubmitEvent(): OnSubmit = TODO()