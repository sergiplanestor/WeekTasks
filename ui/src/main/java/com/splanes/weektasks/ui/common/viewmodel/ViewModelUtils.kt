package com.splanes.weektasks.ui.common.viewmodel

import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiEvent
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiSideEffect
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.viewmodel.ComponentViewModel

fun <M: UiModel, E: UiEvent, S: UiSideEffect, T> ComponentViewModel<M, E, S>.doOnUiStateReady(
    block: (M) -> T
): T? =
    (uiState.value as? UiState.Ready)?.data?.run(block)
