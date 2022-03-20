package com.splanes.weektasks.ui.common.utils.task.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.weektasks.domain.common.date.calendar
import com.splanes.weektasks.domain.common.date.daysBetween
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.domain.feature.task.model.hasDeadline
import com.splanes.weektasks.ui.common.utils.resources.color

@Composable
fun Task.deadlineContainerColor(): Color =
    if (hasDeadline()) {
        val countdown = calendar().daysBetween(calendar(deadline))
        when {
            countdown <= 1 -> color { error.composite(surface, .15) }
            countdown <= 3 -> color { warning.composite(surface, .15) }
            else -> color { surface }
        }
    } else {
        color { surface }
    }

@Composable
fun Task.deadlineContainerBorder(force: Boolean): BorderStroke? {
    val border = BorderStroke(width = 1.dp, color = color { onSurface.alpha(.1) })
    return when {
        force -> border
        hasDeadline() && calendar().daysBetween(calendar(deadline)) > 3 -> border
        !hasDeadline() -> border
        else -> null
    }
}