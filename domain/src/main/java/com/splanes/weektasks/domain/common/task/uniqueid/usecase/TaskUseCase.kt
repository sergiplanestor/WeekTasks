package com.splanes.weektasks.domain.common.task.uniqueid.usecase

import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import com.splanes.weektasks.domain.common.task.uniqueid.repository.UniqueTaskIdRepository
import com.splanes.weektasks.domain.common.task.uniqueid.request.TaskUniqueIdType
import javax.inject.Inject


class CreateTaskUniqueIdUseCase @Inject constructor(private val repository: UniqueTaskIdRepository) :
    UseCase<TaskUniqueIdType, String> {

    override suspend fun execute(id: String, params: TaskUniqueIdType): String =
        repository.newUniqueId(params)
}