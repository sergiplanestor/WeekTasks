package com.splanes.weektasks.domain.common.task.uniqueid.repository

import com.splanes.weektasks.domain.common.task.uniqueid.request.TaskUniqueIdType

interface UniqueTaskIdRepository {
    suspend fun newUniqueId(type: TaskUniqueIdType): String
}