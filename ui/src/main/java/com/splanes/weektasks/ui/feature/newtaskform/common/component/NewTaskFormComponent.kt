package com.splanes.weektasks.ui.feature.newtaskform.common.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.ui.feature.newtaskform.todotask.component.NewTodoTaskForm

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewTaskForm(type: Task.Type, onNewTaskCreated: () -> Unit) {
    when (type) {
        Task.Type.ToDo -> NewTodoTaskForm(onNewTaskCreated)
        Task.Type.Daily -> TODO() /* NewDailyTaskForm() */
    }
}