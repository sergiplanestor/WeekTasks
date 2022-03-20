package com.splanes.weektasks.domain.feature.task.todotask.usecase

import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import com.splanes.weektasks.domain.feature.task.todotask.repository.TodoTasksRepository
import javax.inject.Inject

class DeleteAllDebugUseCase @Inject constructor(
    private val repository: TodoTasksRepository
) : UseCase<Unit, Unit> {

    override suspend fun execute(id: String, params: Unit) {
        repository.deleteAll()
    }

    companion object {
        const val ID = "UseCase.DebugDeleteAll"
    }
}