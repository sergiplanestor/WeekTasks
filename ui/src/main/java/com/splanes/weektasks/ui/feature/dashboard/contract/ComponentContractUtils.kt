package com.splanes.weektasks.ui.feature.dashboard.contract

import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState

fun UiState<DashboardUiModel>.isEditModelEnabled(): Boolean =
    this is UiState.Ready && this.data.isEditModeEnabled