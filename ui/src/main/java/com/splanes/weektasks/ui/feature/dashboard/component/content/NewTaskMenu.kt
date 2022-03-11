package com.splanes.weektasks.ui.feature.dashboard.component.content

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors
import com.splanes.toolkit.compose.ui.theme.utils.accessors.ComponentPaddings
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Display
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Typographies
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.ui.common.spacer.column.Space
import com.splanes.weektasks.ui.common.utils.Drawables
import com.splanes.weektasks.ui.common.utils.Strings
import com.splanes.weektasks.ui.common.utils.expandVertically
import com.splanes.weektasks.ui.common.utils.painter
import com.splanes.weektasks.ui.common.utils.shrinkVertically
import com.splanes.weektasks.ui.common.utils.string
import com.splanes.weektasks.ui.feature.newtaskform.common.component.Animated

@Composable
fun NewTaskMenu(
    modifier: Modifier = Modifier,
    isEditModeEnabled: Boolean,
    onNewTaskModal: (Task.Type) -> Unit,
) {
    Animated(
        modifier = modifier,
        isVisible = !isEditModeEnabled,
        duration = 250
    ) {
        Column(
            modifier = modifier.wrapContentSize(),
            horizontalAlignment = Alignment.End
        ) {
            var isMenuOpen: Boolean by remember { mutableStateOf(false) }
            val onMenuStateChange = { isMenuOpen = !isMenuOpen }

            MenuItems(
                modifier = Modifier.padding(horizontal = 2.dp),
                isVisible = isMenuOpen,
                onItemClick = {
                    onNewTaskModal(it)
                    onMenuStateChange()
                }
            )

            MenuTriggerButton(
                modifier = Modifier,
                isMenuOpen = isMenuOpen,
                onClick = onMenuStateChange
            )
        }
    }
}

@Composable
fun MenuTriggerButton(
    modifier: Modifier,
    isMenuOpen: Boolean,
    onClick: () -> Unit
) {

    val background by animateColorAsState(
        targetValue = (if (isMenuOpen) Colors.primary else Colors.tertiary).composite(
            Colors.surface,
            alpha = .75
        )
    )
    val tint by animateColorAsState(
        targetValue = (if (isMenuOpen) Colors.onPrimary else Colors.onTertiary).alpha(.85)
    )

    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        backgroundColor = background
    ) {
        val close = rememberVectorPainter(image = Icons.Rounded.Close)
        Icon(
            modifier = Modifier.size(size = 25.dp),
            painter = if (isMenuOpen) close else painter { Drawables.ic_menu_burger },
            tint = tint,
            contentDescription = string {
                if (isMenuOpen) {
                    Strings.content_desc_close_new_task_menu
                } else {
                    Strings.content_desc_open_new_task_menu
                }
            }
        )
    }
}

@Composable
fun MenuItems(
    modifier: Modifier,
    isVisible: Boolean,
    onItemClick: (Task.Type) -> Unit
) {
    Animated(
        modifier = modifier,
        isVisible = isVisible,
        duration = 300,
        enter = expandVertically(duration = 100),
        exit = shrinkVertically(duration = 700)
    ) {
        Column(modifier = modifier, horizontalAlignment = Alignment.End) {
            NewTaskButton(
                text = Strings.todo,
                icon = Drawables.ic_task_todo,
                iconContentDescription = Strings.content_desc_add_new_todo_task,
                onClick = { onItemClick(Task.Type.ToDo) }
            )
            Space { mediumSmall }
            NewTaskButton(
                text = Strings.daily,
                icon = Drawables.ic_task_event,
                iconContentDescription = Strings.content_desc_add_new_daily_task,
                onClick = { onItemClick(Task.Type.Daily) }
            )
            Space { medium }
        }
    }
}

@Composable
private fun NewTaskButton(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    @StringRes iconContentDescription: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick, role = Role.Button)
            .background(color = Color.Transparent, shape = RoundedCornerShape(size = 12.dp))
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = ComponentPaddings.small),
            text = string { text },
            style = Typographies.Display.small.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            ),
            color = Colors.onSurface.composite(Colors.surface, .75)
        )
        IconButton(
            modifier = Modifier.background(
                color = Colors.tertiary,
                shape = RoundedCornerShape(16.dp)
            ),
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier
                    .size(size = 25.dp)
                    .align(Alignment.CenterVertically),
                painter = painter { icon },
                tint = Colors.onTertiary.composite(Colors.tertiary, .75),
                contentDescription = string { iconContentDescription }
            )
        }
    }
}
