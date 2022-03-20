package com.splanes.weektasks.data.feature.task.todotask.repositoryimpl

import android.content.res.AssetManager
import com.splanes.weektasks.data.common.repositoryimpl.RepositoryImpl
import com.splanes.weektasks.data.common.utils.load
import com.splanes.weektasks.data.feature.task.todotask.debug.TodoTasksWrapper
import com.splanes.weektasks.data.feature.task.todotask.mapper.TodoTaskMapper
import com.splanes.weektasks.data.feature.task.todotask.persistance.dao.TodoTaskDao
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import com.splanes.weektasks.domain.feature.task.todotask.repository.TodoTasksRepository
import javax.inject.Inject

class TodoTasksRepositoryImpl @Inject constructor(
    private val assets: AssetManager,
    private val dao: TodoTaskDao,
    private val mapper: TodoTaskMapper
) : RepositoryImpl(), TodoTasksRepository {

    override suspend fun populate() = exec {
        if (fetchAll().isEmpty()) {
            assets.load<TodoTasksWrapper>(MOCK_TODO_TASKS_FILE).run {
                tasks.forEach {
                    dao.insert(it)
                }
            }
        }
    }

    override suspend fun fetchAll(): List<TodoTask> = exec {
        dao.fetchAll().map(mapper::map)
    }

    override suspend fun create(task: TodoTask): TodoTask = exec {
        dao.insert(task.let(mapper::map))
        task
    }

    override suspend fun update(task: TodoTask): TodoTask = exec {
        dao.update(task.let(mapper::map))
        task
    }

    override suspend fun delete(task: TodoTask): TodoTask = exec {
        dao.delete(task.let(mapper::map))
        task
    }

    override suspend fun deleteAll() = exec {
        dao.deleteAll()
    }

    companion object {
        private const val MOCK_TODO_TASKS_FILE = "todo-tasks.json"
    }
}