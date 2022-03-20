package com.splanes.weektasks.ui.feature.newtaskform.todotask.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.utils.UiSideEffect
import com.splanes.toolkit.compose.base_arch.logger.e
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.ui.common.component.spacer.row.Weight
import com.splanes.weektasks.ui.common.utils.resources.Drawables
import com.splanes.weektasks.ui.common.utils.resources.Strings
import com.splanes.weektasks.ui.common.utils.resources.body
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.resources.dp
import com.splanes.weektasks.ui.common.utils.resources.painter
import com.splanes.weektasks.ui.common.utils.resources.shape
import com.splanes.weektasks.ui.common.utils.resources.string
import com.splanes.weektasks.ui.feature.newtaskform.common.component.TaskDeadlineFormField
import com.splanes.weektasks.ui.feature.newtaskform.common.component.TaskNotesFormField
import com.splanes.weektasks.ui.feature.newtaskform.common.component.TaskRemindersFormField
import com.splanes.weektasks.ui.feature.newtaskform.common.component.TaskTitleAndPriorityFormField
import com.splanes.weektasks.ui.feature.newtaskform.todotask.contract.NewTodoTaskFormEvent
import com.splanes.weektasks.ui.feature.newtaskform.todotask.contract.NewTodoTaskFormField
import com.splanes.weektasks.ui.feature.newtaskform.todotask.contract.NewTodoTaskFormUiModel
import com.splanes.weektasks.ui.feature.newtaskform.todotask.contract.OnFieldChanged
import com.splanes.weektasks.ui.feature.newtaskform.todotask.contract.OnSubmit
import com.splanes.weektasks.ui.feature.newtaskform.todotask.contract.OnTaskCreated
import com.splanes.weektasks.ui.feature.newtaskform.todotask.contract.OnTaskCreationError
import com.splanes.weektasks.ui.feature.newtaskform.todotask.viewmodel.NewTodoTaskViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewTodoTaskForm(onNewTaskCreated: () -> Unit) {

    val viewModel: NewTodoTaskViewModel = hiltViewModel()

    val onValueChanged: (NewTodoTaskFormField, Any?) -> Unit = { id, value ->
        viewModel.onUiEvent(OnFieldChanged(id, value))
    }

    UiSideEffect(flow = viewModel.uiSideEffect) {
        when (this) {
            OnTaskCreated -> onNewTaskCreated()
            OnTaskCreationError -> {
                // TODO
                e("FORM ERROR")
            }
        }
    }

    var isFullScreen by remember { mutableStateOf(false) }
    var sheetHeightModifier by remember { mutableStateOf(Modifier.fillMaxWidth()) }
    val onSheetHeightChanged: (Boolean) -> Unit = { expand ->
        if (isFullScreen != expand) {
            isFullScreen = expand
            sheetHeightModifier =
                if (isFullScreen) sheetHeightModifier.fillMaxHeight() else Modifier.fillMaxWidth()
        }
    }

    val focusManager = LocalFocusManager.current
    var focusedField by remember { mutableStateOf<NewTodoTaskFormField?>(null) }
    val onFocusChanged: (NewTodoTaskFormField?, FocusRequester?) -> Unit = { field, focusReq ->
        if (focusedField != field) {
            focusedField = field
            focusManager.clearFocus()
            focusReq?.requestFocus()
        }
    }

    (viewModel.uiState.value as? UiState.Ready)?.data?.run {

        val contentModifier = Modifier.padding(horizontal = dp { medium })

        Column(
            modifier = sheetHeightModifier
                .pointerInput(Unit) { detectTapGestures(onTap = { onSheetHeightChanged(true) }) }
                .background(color { surface })
                .padding(bottom = dp { medium })
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dp { tiny })
            ) {
                ModalExpandCollapseButton(
                    isFullScreen = isFullScreen,
                    onClick = {
                        if (isFullScreen) onFocusChanged(null, null)
                        onSheetHeightChanged(!isFullScreen)
                    }
                )
                Weight()
                TextButton(
                    modifier = Modifier
                        .padding(top = dp { small }, end = dp { small })
                        .wrapContentSize(),
                    shape = shape(size = 16),
                    colors = createButtonColors(),
                    onClick = { onSubmit(model = this@run, viewModel::onUiEvent) }
                ) {
                    Text(text = string { Strings.button_create }, style = body { medium })
                }
            }


            TaskTitleAndPriorityFormField(
                modifier = contentModifier,
                title = title.value?.toString(),
                priority = Task.Priority.from(priority.value?.toString()?.toInt()),
                onTitleChanged = { onValueChanged(title.id, it) },
                onPriorityChanged = { onValueChanged(priority.id, it.weight) },
                onFocusChanged = { onFocusChanged(title.id, it) }
            )

            Separator()

            TaskNotesFormField(
                modifier = contentModifier,
                isFocused = focusedField == notes.id,
                notes = notes.value?.toString(),
                onNotesChanged = { onValueChanged(notes.id, it) },
                onFocusChanged = { onFocusChanged(notes.id, it) }
            )

            Separator()

            TaskDeadlineFormField(
                modifier = contentModifier,
                isFocused = focusedField == deadline.id,
                deadline = deadline.value?.toString(),
                onDeadlineChanged = { onValueChanged(deadline.id, it) },
                onFocusChanged = { onFocusChanged(deadline.id, null) }
            )

            Separator()

            TaskRemindersFormField(
                modifier = contentModifier,
                reminders = reminders.value?.toString(),
                onRemindersChanged = { onValueChanged(reminders.id, it) }
            )
        }
    }
}

@Composable
private fun ModalExpandCollapseButton(
    isFullScreen: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painter { if (isFullScreen) Drawables.ic_collapse else Drawables.ic_expand },
            contentDescription = string {
                if (isFullScreen) Strings.content_desc_collapse_modal else Strings.content_desc_expand_modal
            },
            tint = color { secondary.alpha(.65f) }
        )
    }
}

@Composable
private fun Separator() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dp { mediumLarge },
                vertical = dp { small }
            ),
        color = color { onSurface.alpha(.1) }
    )
}

@Composable
private fun createButtonColors(): ButtonColors =
    ButtonDefaults.textButtonColors(
        containerColor = color { primaryContainer.alpha(.6) },
        contentColor = color { onPrimaryContainer.alpha(.8) },
        disabledContainerColor = color { primaryContainer.alpha(.3) },
        disabledContentColor = color { onPrimaryContainer.alpha(.5) }
    )

private fun onSubmit(model: NewTodoTaskFormUiModel, onEvent: (NewTodoTaskFormEvent) -> Unit) {
    val title = model.title.value?.toString()
    val priority = model.priority.value?.toString()?.toInt()
    val notes = model.notes.value?.toString()
    val deadline = model.deadline.value?.toString()?.toLong()
    onEvent(
        OnSubmit(
            priority = priority,
            title = title,
            notes = notes,
            deadline = deadline
        )
    )
}