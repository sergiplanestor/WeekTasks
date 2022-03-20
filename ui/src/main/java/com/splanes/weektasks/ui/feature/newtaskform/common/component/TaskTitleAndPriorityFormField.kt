package com.splanes.weektasks.ui.feature.newtaskform.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Headline
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Typographies
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.ui.R
import com.splanes.weektasks.ui.common.utils.task.ui.color
import com.splanes.weektasks.ui.common.utils.task.ui.icon
import com.splanes.weektasks.ui.common.component.spacer.row.Space
import com.splanes.weektasks.ui.common.utils.transition.EnterTransitionType
import com.splanes.weektasks.ui.common.utils.transition.ExitTransitionType
import com.splanes.weektasks.ui.common.utils.transition.enterTransition
import com.splanes.weektasks.ui.common.utils.transition.exitTransition
import com.splanes.weektasks.ui.common.utils.resources.painter
import com.splanes.weektasks.ui.common.utils.resources.string

@Composable
fun TaskTitleAndPriorityFormField(
    modifier: Modifier = Modifier,
    title: String?,
    onTitleChanged: (String) -> Unit,
    priority: Task.Priority = Task.Priority.Medium,
    onPriorityChanged: (Task.Priority) -> Unit,
    onFocusChanged: (FocusRequester?) -> Unit
) {
    Row(
        modifier = modifier
            .height(height = 60.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        var isPriorityPickerOpen by remember { mutableStateOf(false) }
        val changePriorityPickerState = {
            isPriorityPickerOpen = !isPriorityPickerOpen
            if (isPriorityPickerOpen) onFocusChanged(null)
        }

        TaskTitleField(
            modifier = Modifier.weight(1f),
            title = title,
            onTitleChanged = onTitleChanged,
            onFocusChanged = onFocusChanged
        )

        TaskPriorityPicker(
            modifier = Modifier.wrapContentWidth(),
            isPickerOpen = isPriorityPickerOpen,
            priority = priority,
            onPriorityChanged = { selected ->
                if (selected != priority) onPriorityChanged(selected)
                changePriorityPickerState()
            },
            onPickerStateChanged = changePriorityPickerState
        )
    }
}

@Composable
fun TaskTitleField(
    modifier: Modifier = Modifier,
    title: String?,
    onTitleChanged: (String) -> Unit,
    onFocusChanged: (FocusRequester) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    TextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { state -> if (state.hasFocus) onFocusChanged(focusRequester) },
        value = title.orEmpty(),
        onValueChange = onTitleChanged,
        textStyle = Typographies.Headline.small,
        singleLine = true,
        colors = titleFieldColors(),
        placeholder = {
            Text(
                text = string { R.string.task_name_placeholder },
                style = Typographies.Headline.small,
                color = Colors.onSurface.composite(Colors.surface, alpha = .45)
            )
        }
    )
}

@Composable
fun TaskPriorityPicker(
    modifier: Modifier = Modifier,
    isPickerOpen: Boolean,
    priority: Task.Priority,
    onPriorityChanged: (Task.Priority) -> Unit,
    onPickerStateChanged: () -> Unit
) {
    val backgroundColor = if (isPickerOpen) {
        Colors.onSurface.alpha(.1)
    } else {
        Colors.surface
    }
    Row(
        modifier = modifier.background(
            color = backgroundColor,
            shape = RoundedCornerShape(size = 18.dp)
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TaskPriorityIcon(
            tint = { composite(other = backgroundColor, alpha = .85) },
            priority = priority,
            onClick = onPickerStateChanged
        )
        Animated(
            isVisible = isPickerOpen,
            enter = enterTransition(EnterTransitionType.ExpandHorizontally),
            exit = exitTransition(ExitTransitionType.ShrinkHorizontally)
        ) {
            LazyRow(verticalAlignment = Alignment.CenterVertically) {
                itemsIndexed(Task.Priority.values().filter { it != priority }) { index, item ->
                    if (index == 0) {
                        Space { mediumSmall }
                    }
                    TaskPriorityIcon(
                        tint = {
                            composite(
                                other = backgroundColor,
                                alpha = .85
                            )
                        },
                        priority = item,
                        onClick = { onPriorityChanged(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskPriorityIcon(
    priority: Task.Priority,
    tint: @Composable Color.() -> Color = { this },
    onClick: () -> Unit = {}
) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painter { priority.icon() },
            tint = priority.color().tint(),
            contentDescription = null
        )
    }
}

@Composable
private fun titleFieldColors(): TextFieldColors = TextFieldDefaults.textFieldColors(
    textColor = Colors.onSurface.alpha(alpha = .75),
    cursorColor = Colors.primary.composite(Colors.surface, alpha = .5),
    backgroundColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
)