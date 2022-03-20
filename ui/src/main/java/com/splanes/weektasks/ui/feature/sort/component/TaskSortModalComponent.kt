package com.splanes.weektasks.ui.feature.sort.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.ui.common.component.button.outlined.OutlinedButton
import com.splanes.weektasks.ui.common.component.spacer.column.Space
import com.splanes.weektasks.ui.common.utils.resources.body
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.resources.dp
import com.splanes.weektasks.ui.common.utils.resources.headline
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.UpdateSort
import com.splanes.weektasks.ui.feature.sort.model.TaskSort

@Composable
fun TaskSortModalComponent(
    taskType: Task.Type,
    applied: TaskSort?,
    onUiEvent: (DashboardEvent) -> Unit
) {
    when (taskType) {
        Task.ToDo -> TodoTaskSortModal(applied, onUiEvent)
        Task.Scheduled -> ScheduledTaskSortModal()
    }
}

@Composable
fun TodoTaskSortModal(
    applied: TaskSort?,
    onUiEvent: (DashboardEvent) -> Unit
) {
    var sortCriteria: TaskSort? by remember { mutableStateOf(applied) }
    Column(modifier = Modifier.padding(all = dp { mediumSmall })) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Tasks order",
            style = headline { medium },
            color = color { primary }
        )
        Space { mediumSmall }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Show NOT done")
        }
        Space { large }
        OutlinedButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Apply",
            color = { secondary },
            textStyle = body { large },
            onClick = { onUiEvent(UpdateSort(sortCriteria)) }
        )
        Space { medium }
    }
}

@Composable
fun ScheduledTaskSortModal() {
    TODO()
}