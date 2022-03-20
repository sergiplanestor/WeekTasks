package com.splanes.weektasks.ui.feature.newtaskform.common.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.ui.feature.newtaskform.todotask.component.NewTodoTaskForm

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewTaskFormModalComponent(taskType: Task.Type, onCreated: () -> Unit) {
    when (taskType) {
        Task.ToDo -> NewTodoTaskForm(onCreated)
        Task.Scheduled -> TODO() /* NewDailyTaskForm() */
    }
}