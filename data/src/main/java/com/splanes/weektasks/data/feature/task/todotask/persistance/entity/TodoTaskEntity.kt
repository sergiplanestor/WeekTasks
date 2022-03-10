package com.splanes.weektasks.data.feature.task.todotask.persistance.entity

import androidx.room.*

@Entity(tableName = "task_todo", indices = [Index(value = ["task_id"], unique = true)])
data class TodoTaskEntity(
    @ColumnInfo(name = "task_id")
    val taskId: String,
    @ColumnInfo(name = "task_title")
    val title: String,
    @ColumnInfo(name = "task_notes")
    val notes: String?,
    @ColumnInfo(name = "task_is_done")
    val isDone: Int,
    @ColumnInfo(name = "task_creation_date")
    val createdOn: Long,
    @ColumnInfo(name = "task_update_date")
    val updatedOn: Long?,
    @ColumnInfo(name = "task_mark_as_done_date")
    val markDoneOn: Long?,
    @ColumnInfo(name = "task_priority_weight")
    val priorityWeight: Int,
    @ColumnInfo(name = "task_deadline_on")
    val deadlineOn: Long?,
    /*@ColumnInfo(name = "task_priority_weight")
    val reminders: Int*/
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entry_id")
    var id: Int = 0
}