package com.splanes.weektasks.data.feature.task.todotask.debug

import com.google.gson.annotations.SerializedName
import com.splanes.weektasks.data.feature.task.todotask.persistance.entity.TodoTaskEntity

data class TodoTasksWrapper(
    @SerializedName("todo")
    val tasks: List<TodoTaskEntity>
)
