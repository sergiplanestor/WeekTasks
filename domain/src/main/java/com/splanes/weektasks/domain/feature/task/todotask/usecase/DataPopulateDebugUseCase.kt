package com.splanes.weektasks.domain.feature.task.todotask.usecase

import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import com.splanes.weektasks.domain.feature.task.todotask.repository.TodoTasksRepository
import javax.inject.Inject

class DataPopulateDebugUseCase @Inject constructor(
    private val repository: TodoTasksRepository
) : UseCase<Unit, Unit> {

    override suspend fun execute(id: String, params: Unit) {
        repository.populate()
    }

    companion object {
        const val ID = "UseCase.DebugPopulate"
    }
}