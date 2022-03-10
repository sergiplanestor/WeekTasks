package com.splanes.weektasks.domain.feature.task.todotask.usecase

import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import com.splanes.weektasks.domain.common.date.now
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.domain.common.usecase.uniqueId
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import com.splanes.weektasks.domain.feature.task.todotask.repository.TodoTasksRepository
import javax.inject.Inject

class CreateTodoTaskUseCase @Inject constructor(
    private val repositoryTodoTasks: TodoTasksRepository
) : UseCase<CreateTodoTaskUseCase.Params, TodoTask?> {

    override suspend fun execute(id: String, params: Params): TodoTask {
        val task = TodoTask(
            id = uniqueId(),
            priority = params.priority,
            title = params.title,
            notes = params.notes,
            deadline = params.deadline ?: -1,
            reminders = emptyList() /* FIXME params.reminders */,
            isDone = false,
            createdOn = now(),
            updatedOn = -1,
            markDoneOn = -1
        )
        return repositoryTodoTasks.create(task)
    }

    data class Params(
        val priority: Task.Priority,
        val title: String,
        val notes: String?,
        val deadline: Long?,
        val reminders: List<Any> = emptyList()
    )

    companion object {
        const val ID = "UseCase.CreateTodoTask"
    }
}