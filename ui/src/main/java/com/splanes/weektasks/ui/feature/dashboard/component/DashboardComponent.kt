package com.splanes.weektasks.ui.feature.dashboard.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.splanes.toolkit.compose.base_arch.feature.presentation.component.contract.UiState
import com.splanes.weektasks.ui.feature.dashboard.component.content.TaskTodoCard
import com.splanes.weektasks.ui.feature.dashboard.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardComponent(uiController: SystemUiController) {

    val viewModel: DashboardViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    var isEditModeEnabled: Boolean by remember { mutableStateOf(false) }
    val onEditModeChanged: (isEnabled: Boolean) -> Unit = { isEditModeEnabled = it }

    DashboardContainer(
        uiController = uiController,
        coroutineScope = coroutineScope,
        isEditModeEnabled = isEditModeEnabled,
        onEditModeChanged = onEditModeChanged,
        onUiEvent = viewModel::onUiEvent
    ) {
        (viewModel.uiState.value as? UiState.Ready)?.data?.run {
            TaskTodoCard(todoTasks)
            TaskScheduledCard()
        }
    }
}