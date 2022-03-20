package com.splanes.weektasks.ui.feature.dashboard.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.weektasks.ui.feature.dashboard.component.content.TaskTodoCard
import com.splanes.weektasks.ui.feature.dashboard.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardComponent(uiController: SystemUiController) {

    val viewModel: DashboardViewModel = hiltViewModel()
    var isEditModeEnabled: Boolean by remember { mutableStateOf(false) }
    val onEditModeChanged: (isEnabled: Boolean) -> Unit = { isEditModeEnabled = it }

    val uiModel = (viewModel.uiState.value as? UiState.Ready)?.data

    DashboardContainer(
        uiController = uiController,
        isEditModeEnabled = isEditModeEnabled,
        onEditModeChanged = onEditModeChanged,
        onUiEvent = viewModel::onUiEvent,
        modal = uiModel?.modal
    ) {
        (viewModel.uiState.value as? UiState.Ready)?.data?.run {
            TaskTodoCard(todoTasks, filters, viewModel::onUiEvent)
            // TaskScheduledCard()
        }
    }
}