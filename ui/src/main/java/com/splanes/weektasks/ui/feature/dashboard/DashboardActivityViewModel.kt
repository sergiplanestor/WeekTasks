package com.splanes.weektasks.ui.feature.dashboard

import com.splanes.toolkit.compose.base_arch.feature.presentation.activity.contract.ActivityUiModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.activity.viewmodel.BaseComponentActivityViewModel
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.toolkit.compose.ui.components.feature.scaffold.model.ScaffoldColors
import com.splanes.toolkit.compose.ui.components.feature.statusbar.model.StatusBarColors
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardActivityViewModel @Inject constructor() : BaseComponentActivityViewModel() {

    fun ready(statusBarColors: StatusBarColors, scaffoldColors: ScaffoldColors) {
        updateUiState { UiState.Ready(ActivityUiModel(statusBarColors, scaffoldColors)) }
    }
}