package com.splanes.weektasks.data.feature.task.todotask.persistance.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.splanes.weektasks.data.feature.task.todotask.persistance.entity.TodoTaskEntity

@Dao
interface TodoTaskDao {

    @Query(value = "SELECT * FROM task_todo ORDER BY task_priority_weight DESC")
    suspend fun fetchAll(): List<TodoTaskEntity>

    @Query(value = "SELECT * FROM task_todo WHERE task_priority_weight = :priority ORDER BY task_priority_weight DESC")
    suspend fun fetchByPrior(priority: Int): List<TodoTaskEntity>

    @Insert
    suspend fun insert(entity: TodoTaskEntity)

    @Update
    suspend fun update(entity: TodoTaskEntity)

    @Delete
    suspend fun delete(entity: TodoTaskEntity)
}