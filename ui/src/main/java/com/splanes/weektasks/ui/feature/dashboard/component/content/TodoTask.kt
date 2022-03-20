package com.splanes.weektasks.ui.feature.dashboard.component.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import com.splanes.weektasks.ui.common.component.checkbox.CircularCheckBox
import com.splanes.weektasks.ui.common.component.spacer.column.Space
import com.splanes.weektasks.ui.common.component.spacer.row.Space
import com.splanes.weektasks.ui.common.component.spacer.row.Weight
import com.splanes.weektasks.ui.common.utils.modifier.interaction.collapsedState
import com.splanes.weektasks.ui.common.utils.modifier.interaction.expandedState
import com.splanes.weektasks.ui.common.utils.modifier.interaction.interactionSource
import com.splanes.weektasks.ui.common.utils.modifier.interaction.isExpanded
import com.splanes.weektasks.ui.common.utils.modifier.interaction.not
import com.splanes.weektasks.ui.common.utils.modifier.interaction.onClick
import com.splanes.weektasks.ui.common.utils.modifier.interaction.ripple
import com.splanes.weektasks.ui.common.utils.resources.Drawables
import com.splanes.weektasks.ui.common.utils.resources.Strings
import com.splanes.weektasks.ui.common.utils.resources.body
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.resources.dp
import com.splanes.weektasks.ui.common.utils.resources.headline
import com.splanes.weektasks.ui.common.utils.resources.label
import com.splanes.weektasks.ui.common.utils.resources.painter
import com.splanes.weektasks.ui.common.utils.resources.shape
import com.splanes.weektasks.ui.common.utils.resources.string
import com.splanes.weektasks.ui.common.utils.task.ui.color
import com.splanes.weektasks.ui.common.utils.task.ui.deadlineContainerBorder
import com.splanes.weektasks.ui.common.utils.task.ui.deadlineContainerColor
import com.splanes.weektasks.ui.common.utils.task.ui.icon
import com.splanes.weektasks.ui.common.utils.transition.expandVertically
import com.splanes.weektasks.ui.common.utils.transition.fadeIn
import com.splanes.weektasks.ui.common.utils.transition.fadeOut
import com.splanes.weektasks.ui.common.utils.transition.shrinkVertically
import com.splanes.weektasks.ui.feature.dashboard.contextmenu.component.ToDoContextMenu
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.UpdateTask
import com.splanes.weektasks.ui.feature.filter.model.TaskFilter
import com.splanes.weektasks.ui.feature.newtaskform.common.component.Animated

@Composable
fun TaskTodoCard(
    tasks: List<TodoTask>,
    filters: List<TaskFilter>,
    onUiEvent: (DashboardEvent) -> Unit
) {

    var taskCardExpandableState by remember {
        mutableStateOf(if (tasks.isEmpty()) collapsedState() else expandedState())
    }
    val onChangeCardExpandedState: () -> Unit =
        { taskCardExpandableState = !taskCardExpandableState }

    var isContextMenuVisible by remember { mutableStateOf(false) }
    val onChangeContextMenuVisibility: (Boolean) -> Unit =
        { isVisible -> isContextMenuVisible = isVisible }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = dp { medium }),
        backgroundColor = color { surface },
        shape = shape(size = 8)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dp { small },
                    end = dp { small },
                    bottom = if (taskCardExpandableState.isExpanded()) dp { smallTiny } else 0.dp
                )
        ) {

            TaskTodoCardHeader(
                isExpanded = taskCardExpandableState.isExpanded(),
                isContextMenuVisible = isContextMenuVisible,
                counter = tasks.count(),
                filters = filters,
                onHeaderClick = onChangeCardExpandedState,
                onContextMenuClick = { onChangeContextMenuVisibility(!isContextMenuVisible) },
                onContextMenuItemClick = {
                    onChangeContextMenuVisibility(false)
                    onUiEvent(it)
                }
            )

            AnimatedVisibility(
                visible = taskCardExpandableState.isExpanded(),
                enter = fadeIn() + expandVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
                exit = fadeOut(duration = 300) + shrinkVertically(duration = 500)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dp { small }, bottom = dp { mediumSmall })
                ) {
                    itemsIndexed(tasks) { index, task ->
                        if (index == 0) {
                            Space { smallTiny }
                        }
                        TaskTodoOverview(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = dp { small }),
                            task = task,
                            onMarkDone = { onUiEvent(UpdateTask(task.copy(isDone = true))) }
                        )
                        if (index != tasks.lastIndex) {
                            Space { smallTiny }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskTodoCardHeader(
    isExpanded: Boolean,
    isContextMenuVisible: Boolean,
    filters: List<TaskFilter>,
    counter: Int,
    onHeaderClick: () -> Unit,
    onContextMenuClick: () -> Unit,
    onContextMenuItemClick: (DashboardEvent) -> Unit,
) {

    val topPadding by animateDpAsState(
        targetValue = dp { if (isExpanded) medium else small },
        animationSpec = tween(durationMillis = 250, delayMillis = if (isExpanded) 0 else 200)
    )
    val bottomPadding by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else dp { small },
        animationSpec = tween(durationMillis = 250, delayMillis = if (isExpanded) 0 else 200)
    )
    val fontSize by animateFloatAsState(
        targetValue = headline { small }.fontSize.value.let {
            if (isExpanded) it + 4 else it - 2
        },
        animationSpec = tween(durationMillis = 250, delayMillis = 200)
    )
    val counterAlpha by animateFloatAsState(
        targetValue = if (isExpanded) 0f else 1f,
        animationSpec = tween(durationMillis = 250, delayMillis = 150)
    )
    val contextMenuAlpha by animateFloatAsState(
        targetValue = if (!isExpanded) 0f else 1f,
        animationSpec = tween(durationMillis = 250, delayMillis = 150)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dp { medium },
                top = topPadding,
                bottom = bottomPadding
            )
            .onClick(
                interaction = interactionSource,
                onClick = onHeaderClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = string { Strings.task_todo },
            style = headline { small }.copy(fontSize = fontSize.sp),
            color = color { primary.alpha(.75) }
        )
        Weight()
        if (isExpanded) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .alpha(contextMenuAlpha),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    modifier = Modifier,
                    onClick = onContextMenuClick
                ) {
                    Icon(
                        painter = painter { Drawables.ic_menu_vertical },
                        contentDescription = string { Strings.content_desc_collapse_todo_tasks },
                        tint = color { onSurface.alpha(.5) }
                    )
                }
                ToDoContextMenu(
                    isVisible = isContextMenuVisible,
                    onDismiss = onContextMenuClick,
                    filters = filters,
                    onClick = onContextMenuItemClick
                )
            }
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = dp { small })
                    .alpha(counterAlpha),
                text = string(block = { Strings.task_overview_counter }, counter),
                style = label { large },
                color = color { onSurface.alpha(.35) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTodoOverview(
    modifier: Modifier = Modifier,
    task: TodoTask,
    onMarkDone: () -> Unit
) {
    var isTaskExpanded by remember { mutableStateOf(false) }
    val onChangeTaskExpandState: (Boolean) -> Unit = { expand -> isTaskExpanded = expand }

    val backgroundColor by animateColorAsState(
        targetValue = if (isTaskExpanded) color { surface } else task.deadlineContainerColor(),
        animationSpec = tween(durationMillis = 300)
    )
    val backgroundBorder = task.deadlineContainerBorder(force = isTaskExpanded)

    Surface(
        modifier = modifier,
        shape = shape(size = 6),
        color = backgroundColor,
        border = backgroundBorder,
        contentColor = color { onSurface },
        indication = ripple { onSurface.composite(backgroundColor, .35) }
            .takeIf { !isTaskExpanded },
        interactionSource = interactionSource,
        onClick = { onChangeTaskExpandState(!isTaskExpanded) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = dp { small },
                    vertical = dp { mediumSmall }
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(22.dp),
                    painter = painter { task.priority.icon() },
                    tint = task.priority.color().alpha(.5),
                    contentDescription = null
                )
                Space { medium }
                Text(
                    text = task.title,
                    style = body { large }
                )
                Weight()
                CircularCheckBox(
                    size = 20.dp,
                    isChecked = false,
                    onCheckChange = { isTaskCompleted -> if (isTaskCompleted) onMarkDone() }
                )
            }
            Animated(
                isVisible = isTaskExpanded,
                duration = 200,
                enter = expandVertically(duration = 200),
                exit = shrinkVertically(duration = 200)
            ) {
                TaskTodoOverviewDetails(task = task)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTodoOverviewCollapsed(
    modifier: Modifier = Modifier,
    task: TodoTask,
    onMarkDone: () -> Unit
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTodoOverviewExpanded(
    modifier: Modifier = Modifier,
    task: TodoTask,
    onMarkDone: () -> Unit
) {

}

@Composable
fun TaskTodoOverviewDetails(
    task: TodoTask
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Divider(
            modifier = Modifier.padding(horizontal = dp { medium }),
            color = color { onSurface }.alpha(.2f)
        )
        Space { medium }
        Text(
            modifier = Modifier.padding(horizontal = dp { medium }),
            text = task.notes.orEmpty(),
            style = body { small }.let { it.copy(fontSize = (it.fontSize.value + 2).sp) },
            color = color { onSurface.alpha(.6) },
            textAlign = TextAlign.Justify
        )
        Space { mediumLarge }
    }
}


