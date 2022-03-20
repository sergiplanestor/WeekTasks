package com.splanes.weektasks.ui.feature.filter.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.ui.common.component.spacer.column.Space
import com.splanes.weektasks.ui.common.component.spacer.row.Weight
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.UpdateFilters
import com.splanes.weektasks.ui.feature.filter.model.TaskFilter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskFiltersModalComponent(
    taskType: Task.Type,
    applied: List<TaskFilter>,
    onUiEvent: (DashboardEvent) -> Unit
) {
    when (taskType) {
        Task.ToDo -> TodoTaskFiltersModal(applied) { filter, apply ->
            onUiEvent(UpdateFilters(filter, apply))
        }
        Task.Scheduled -> ScheduledTaskFiltersModal(applied) { filter, apply ->
            onUiEvent(UpdateFilters(filter, apply))
        }
    }
}

@Composable
fun TodoTaskFiltersModal(
    applied: List<TaskFilter>,
    onChange: (TaskFilter, Boolean) -> Unit
) {
    Column {
        Text(text = "Filters")
        Space { mediumSmall }
        Row(modifier = Modifier.fillMaxWidth()) {
            Weight()
            Button(onClick = { onChange(TaskFilter.OnlyPending, false) }) {
                Text(text = "Show all")
            }
            Weight()
            Button(onClick = { onChange(TaskFilter.OnlyPending, true) }) {
                Text(text = "Show NOT done")
            }
            Weight()
        }
        Space { huge }
        Text(text = "WIP")
        Space { huge }
    }
}

@Composable
fun ScheduledTaskFiltersModal(
    applied: List<TaskFilter>,
    onChange: (TaskFilter, Boolean) -> Unit
) {

}