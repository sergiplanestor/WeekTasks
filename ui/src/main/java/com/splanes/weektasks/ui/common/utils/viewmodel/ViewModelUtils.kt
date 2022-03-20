package com.splanes.weektasks.ui.common.utils.viewmodel

import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timerange.TimeRange
import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timestamp.Timestamp
import com.splanes.toolkit.compose.base_arch.feature.domain.common.datetime.timestamp.now
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiEvent
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiSideEffect
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel

fun <M : UiModel, E : UiEvent, S : UiSideEffect, T> ComponentViewModel<M, E, S>.doOnUiStateReady(
    block: (M) -> T
): T? =
    (uiState.value as? UiState.Ready)?.data?.run(block)

val <M : UiModel, E : UiEvent, S : UiSideEffect> ComponentViewModel<M, E, S>.timeout: TimeRange
    get() = with(now()) {
        TimeRange(from = this, to = Timestamp(this.millis + 20000L))
    }
