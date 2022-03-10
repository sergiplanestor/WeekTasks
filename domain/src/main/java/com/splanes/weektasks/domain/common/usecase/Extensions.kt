package com.splanes.weektasks.domain.common.usecase

import com.splanes.toolkit.compose.base_arch.feature.domain.usecase.UseCase
import java.util.UUID

fun <P, D, UC : UseCase<P, D>> UC.useCaseId(): String =
    "WeekTasks.${this::class.simpleName ?: "[Unknown UseCase]"}"

fun <P, D> UseCase<P, D>.uniqueId(): String =
    UUID.randomUUID().toString().replace("-", "")