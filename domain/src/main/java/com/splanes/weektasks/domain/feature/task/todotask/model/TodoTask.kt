package com.splanes.weektasks.domain.feature.task.todotask.model

import com.splanes.weektasks.domain.feature.task.model.Reminder
import com.splanes.weektasks.domain.feature.task.model.Task

data class TodoTask(
    override val id: String,
    override val title: String,
    override val notes: String?,
    override val isDone: Boolean,
    override val priority: Priority,
    override val deadline: Long,
    override val reminders: List<Reminder>,
    override val markDoneOn: Long,
    override val createdOn: Long,
    override val updatedOn: Long
) : Task() {
    override val type = ToDo
}
