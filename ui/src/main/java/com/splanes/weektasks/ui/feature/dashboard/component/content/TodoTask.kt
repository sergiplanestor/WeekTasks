package com.splanes.weektasks.ui.feature.dashboard.component.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.weektasks.domain.common.date.calendar
import com.splanes.weektasks.domain.common.date.daysBetween
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import com.splanes.weektasks.ui.common.priority.color
import com.splanes.weektasks.ui.common.priority.icon
import com.splanes.weektasks.ui.common.spacer.column.Space
import com.splanes.weektasks.ui.common.spacer.row.Space
import com.splanes.weektasks.ui.common.spacer.row.Weight
import com.splanes.weektasks.ui.common.utils.Drawables
import com.splanes.weektasks.ui.common.utils.Strings
import com.splanes.weektasks.ui.common.utils.body
import com.splanes.weektasks.ui.common.utils.color
import com.splanes.weektasks.ui.common.utils.dp
import com.splanes.weektasks.ui.common.utils.fadeOut
import com.splanes.weektasks.ui.common.utils.headline
import com.splanes.weektasks.ui.common.utils.label
import com.splanes.weektasks.ui.common.utils.painter
import com.splanes.weektasks.ui.common.utils.shape
import com.splanes.weektasks.ui.common.utils.shrinkVertically
import com.splanes.weektasks.ui.common.utils.string
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardEvent

@Composable
fun TaskTodoCard(
    tasks: List<TodoTask>,
    onUiEvent: (DashboardEvent) -> Unit
) {

    var isExpanded by remember { mutableStateOf(true) }
    val onChangeExpandState: (Boolean) -> Unit = { expand -> isExpanded = expand }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = dp { medium })
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                enabled = !isExpanded,
                onClick = { onChangeExpandState(true) }
            )
        ,
        backgroundColor = color { surface },
        shape = shape(size = 8)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dp { small },
                    end = dp { small },
                    bottom = if (isExpanded) dp { smallTiny } else 0.dp
                )
        ) {

            TaskTodoCardHeader(
                isExpanded = isExpanded,
                counter = tasks.count(),
                onClick = { onChangeExpandState(false) }
            )

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
                exit = fadeOut(duration = 500) + shrinkVertically(duration = 500)
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
                            onMarkDone = { onUiEvent(DashboardEvent.UpdateTask(task.copy(isDone = it))) }
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

@Composable
fun TaskTodoCardHeader(
    isExpanded: Boolean,
    counter: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dp { medium },
                top = dp { if (isExpanded) medium else small },
                bottom = if (isExpanded) 0.dp else dp { small }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = string { Strings.task_todo },
            style = headline { small }.let { if (isExpanded) it.copy(fontSize = (it.fontSize.value + 4).sp) else it },
            color = color { primary.alpha(.75) }
        )
        Weight()
        if (isExpanded) {
            IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = onClick) {
                Icon(
                    painter = painter { Drawables.ic_chevron_down },
                    contentDescription = string { Strings.content_desc_collapse_todo_tasks },
                    tint = color { onSurface.alpha(.5) }
                )
            }
            Space { mediumSmall }
            IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = onClick) {
                Icon(
                    painter = painter { Drawables.ic_chevron_down },
                    contentDescription = string { Strings.content_desc_collapse_todo_tasks },
                    tint = color { onSurface.alpha(.5) }
                )
            }
        } else {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
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
    onMarkDone: (Boolean) -> Unit
) {
    var isTaskExpanded by remember { mutableStateOf(false) }
    val onChangeTaskExpandState: (Boolean) -> Unit = { expand -> isTaskExpanded = expand }
    val (backgroundColor, backgroundBorder) = taskOverviewBackground(isTaskExpanded, task.deadline)

    Surface(
        modifier = modifier,
        shape = shape(size = 6),
        color = backgroundColor,
        border = backgroundBorder,
        contentColor = color { onSurface },
        indication = null,
        interactionSource = MutableInteractionSource(),
        onClick = { onChangeTaskExpandState(!isTaskExpanded) }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = dp { small }, vertical = dp { mediumSmall }),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painter { task.priority.icon() },
                tint = task.priority.color().alpha(.5),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = dp { medium }),
                text = task.title,
                style = body { large }
            )
            Weight()
            Checkbox(
                modifier = Modifier.size(22.dp),
                checked = task.isDone,
                onCheckedChange = { onMarkDone(it) }
            )
        }
    }
}

@Composable
private fun taskOverviewBackground(
    isExpanded: Boolean,
    deadlineOn: Long
): Pair<Color, BorderStroke?> =
    when {
        !isExpanded && deadlineOn != -1L -> {
            val countdown = calendar().daysBetween(calendar(deadlineOn))
            when {
                countdown <= 1 -> color { errorContainer.composite(surface, .15) } to null
                countdown <= 3 -> color { warningContainer.composite(surface, .15) } to null
                else -> color { surface } to BorderStroke(
                    width = 1.dp,
                    color = color { onSurface.alpha(.1) }
                )
            }
        }
        else -> color { surface } to BorderStroke(
            width = 1.dp,
            color = color { onSurface.alpha(.1) }
        )
    }