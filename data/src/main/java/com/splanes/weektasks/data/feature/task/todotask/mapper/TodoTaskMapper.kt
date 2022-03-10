package com.splanes.weektasks.data.feature.task.todotask.mapper

import com.splanes.weektasks.data.common.utils.orDefault
import com.splanes.weektasks.data.common.utils.toBoolean
import com.splanes.weektasks.data.common.utils.toInt
import com.splanes.weektasks.data.feature.task.todotask.persistance.entity.TodoTaskEntity
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import javax.inject.Inject

class TodoTaskMapper @Inject constructor() {

    fun map(entity: TodoTaskEntity): TodoTask =
        with(entity) {
            TodoTask(
                id = taskId,
                priority = Task.Priority.from(weight = priorityWeight),
                title = title,
                notes = notes,
                isDone = isDone.toBoolean(),
                deadline = deadlineOn ?: -1,
                reminders = emptyList(),
                createdOn = createdOn,
                updatedOn = updatedOn.orDefault(),
                markDoneOn = markDoneOn ?: -1
            )
        }

    fun map(task: TodoTask): TodoTaskEntity =
        with(task) {
            TodoTaskEntity(
                taskId = id,
                priorityWeight = priority.weight,
                title = title,
                notes = notes,
                isDone = isDone.toInt(),
                deadlineOn = deadline,
                /* FIXME: reminders = reminders */
                createdOn = createdOn,
                updatedOn = updatedOn,
                markDoneOn = markDoneOn
            )
        }
}