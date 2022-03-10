package com.splanes.weektasks.domain.feature.task.todotask.repository

import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask

interface TodoTasksRepository {

    suspend fun fetchAll(): List<TodoTask>
    suspend fun create(task: TodoTask): TodoTask
    suspend fun update(task: TodoTask): TodoTask
    suspend fun delete(task: TodoTask): TodoTask
}