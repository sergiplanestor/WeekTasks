package com.splanes.weektasks.domain.feature.task.todotask.usecase

import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import com.splanes.weektasks.domain.feature.task.todotask.repository.TodoTasksRepository
import javax.inject.Inject

class FetchTodoTasksUseCase @Inject constructor(
    private val repositoryTodoTasks: TodoTasksRepository
) : UseCase<FetchTodoTasksUseCase.Params, List<TodoTask>> {

    override suspend fun execute(id: String, params: Params): List<TodoTask> =
        repositoryTodoTasks.fetchAll()

    object Params

    companion object {
        const val ID = "UseCase.FetchTodoTasks"
    }
}