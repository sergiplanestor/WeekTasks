package com.splanes.weektasks.ui.feature.dashboard.contextmenu.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.ui.common.component.button.icon.IconButton
import com.splanes.weektasks.ui.common.component.spacer.row.Space
import com.splanes.weektasks.ui.common.utils.any.isNotNull
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.resources.painter
import com.splanes.weektasks.ui.common.utils.resources.string
import com.splanes.weektasks.ui.common.utils.resources.title
import com.splanes.weektasks.ui.feature.dashboard.contextmenu.Filter
import com.splanes.weektasks.ui.feature.dashboard.contextmenu.Sort
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.OpenTaskFiltersModal
import com.splanes.weektasks.ui.feature.dashboard.contract.OpenTaskSortModal
import com.splanes.weektasks.ui.feature.dashboard.contract.RemoveFilters
import com.splanes.weektasks.ui.feature.filter.model.TaskFilter
import com.splanes.weektasks.ui.feature.sort.model.TaskSort

@Composable
fun ToDoContextMenu(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    filters: List<TaskFilter> = emptyList(),
    sort: TaskSort? = null,
    onClick: (DashboardEvent) -> Unit,
) {
    val options = mutableListOf(
        Filter(
            isSelected = filters.isNotEmpty(),
            onClick = { onClick(OpenTaskFiltersModal(Task.ToDo, filters)) }
        ),
        Sort(
            isSelected = sort.isNotNull(),
            onClick = { onClick(OpenTaskSortModal(Task.ToDo, sort)) }
        )
    )

    DropdownMenu(expanded = isVisible, onDismissRequest = onDismiss) {
        options.forEach { model ->
            DropdownMenuItem(contentPadding = PaddingValues(0.dp), onClick = model.onClick) {
                Space { mediumSmall }
                Icon(
                    modifier = Modifier.align(Alignment.CenterVertically).size(20.dp),
                    painter = painter { model.icon },
                    tint = color { if (model.isSelected) primary.alpha(.75) else onSurface.alpha(.5) },
                    contentDescription = null
                )
                Space { small }
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = string { model.text },
                    style = title { medium },
                    maxLines = 1,
                    color = color { if (model.isSelected) primary.alpha(.75) else onSurface.alpha(.5) }
                )
                if (model.isSelected) {
                    Space { tiny }
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterVertically).wrapContentWidth(),
                        icon = Icons.Rounded.Close,
                        onClick = { onClick(RemoveFilters) },
                        tint = color { error.composite(surface, .5) },
                        size = 20.dp
                    )
                }
                Space { mediumSmall }
            }
        }
    }
}