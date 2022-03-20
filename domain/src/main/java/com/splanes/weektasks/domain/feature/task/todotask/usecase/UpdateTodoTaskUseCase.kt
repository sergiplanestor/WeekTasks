package com.splanes.weektasks.domain.feature.task.todotask.usecase

import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import com.splanes.weektasks.domain.feature.task.todotask.repository.TodoTasksRepository
import javax.inject.Inject

class UpdateTodoTaskUseCase @Inject constructor(
    private val repositoryTodoTasks: TodoTasksRepository
) : UseCase<UpdateTodoTaskUseCase.Params, TodoTask> {

    override suspend fun execute(id: String, params: Params): TodoTask =
        repositoryTodoTasks.update(params.task)

    data class Params(val task: TodoTask)

    companion object {
        const val ID = "UseCase.UpdateTodoTask"
    }
}