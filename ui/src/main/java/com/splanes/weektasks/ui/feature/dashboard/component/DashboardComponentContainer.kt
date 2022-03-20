package com.splanes.weektasks.ui.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Shapes
import com.splanes.toolkit.compose.ui.theme.utils.accessors.ViewportPaddings
import com.splanes.weektasks.ui.feature.dashboard.component.content.AppBar
import com.splanes.weektasks.ui.feature.dashboard.component.content.NewTaskMenu
import com.splanes.weektasks.ui.feature.dashboard.contract.AutoPopulate
import com.splanes.weektasks.ui.feature.dashboard.contract.CloseModalEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardModalEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.LoadTasks
import com.splanes.weektasks.ui.feature.dashboard.contract.OpenModalEvent
import com.splanes.weektasks.ui.feature.dashboard.contract.OpenNewTaskModal
import com.splanes.weektasks.ui.feature.dashboard.contract.OpenTaskFiltersModal
import com.splanes.weektasks.ui.feature.dashboard.contract.OpenTaskSortModal
import com.splanes.weektasks.ui.feature.dashboard.contract.RemoveDatabase
import com.splanes.weektasks.ui.feature.dashboard.contract.RemoveTasksSelected
import com.splanes.weektasks.ui.feature.filter.component.TaskFiltersModalComponent
import com.splanes.weektasks.ui.feature.newtaskform.common.component.NewTaskFormModalComponent
import com.splanes.weektasks.ui.feature.sort.component.TaskSortModalComponent

@OptIn(ExperimentalMaterialApi::class)
typealias Modal = BottomSheetValue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DashboardContainer(
    uiController: SystemUiController,
    isEditModeEnabled: Boolean,
    onEditModeChanged: (isEnabled: Boolean) -> Unit,
    onUiEvent: (DashboardEvent) -> Unit,
    modal: DashboardModalEvent?,
    content: @Composable ColumnScope.() -> Unit
) {

    val bottomSheetState = rememberBottomSheetState(
        initialValue = Modal.Collapsed,
        confirmStateChange = {
            if (it == Modal.Collapsed) onUiEvent(CloseModalEvent)
            true
        }
    )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    LaunchedEffect("Kei") {
        scaffoldState.bottomSheetState.animateTo(
            if (modal != null) Modal.Expanded else Modal.Collapsed
        )
    }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetContent = {
            if (modal is OpenModalEvent) {
                DashboardModalComponent(
                    modal = modal,
                    dismiss = { onUiEvent(CloseModalEvent) },
                    onUiEvent = onUiEvent
                )
            }
        },
        sheetShape = Shapes.large.copy(
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp)
        ),
        sheetPeekHeight = 0.dp,
        topBar = {
            AppBar(
                uiController = uiController,
                isEditModeEnabled = isEditModeEnabled,
                onEditModeChanged = onEditModeChanged,
                onDeleteDatabase = { onUiEvent(RemoveDatabase) },
                onAutoPopulateDatabase = { onUiEvent(AutoPopulate) },
                onRemoveSelectedTasks = { onUiEvent(RemoveTasksSelected) }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.inverseSurface.composite(Colors.surface, .2))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.Top),
                content = content
            )
            NewTaskMenu(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = ViewportPaddings.mediumSmall, bottom = ViewportPaddings.medium),
                isEditModeEnabled = isEditModeEnabled,
                onNewTaskModal = { type -> onUiEvent(OpenNewTaskModal(type)) }
            )
        }
    }
}

@Composable
fun DashboardModalComponent(
    modal: OpenModalEvent,
    dismiss: () -> Unit,
    onUiEvent: (DashboardEvent) -> Unit
) {
    when (modal) {
        is OpenNewTaskModal -> {
            NewTaskFormModalComponent(
                taskType = modal.taskType,
                onCreated = {
                    dismiss()
                    onUiEvent(LoadTasks)
                }
            )
        }
        is OpenTaskFiltersModal -> {
            TaskFiltersModalComponent(
                taskType = modal.type,
                applied = modal.applied,
                onUiEvent = { event ->
                    dismiss()
                    onUiEvent(event)
                }
            )
        }
        is OpenTaskSortModal -> {
            TaskSortModalComponent(
                taskType = modal.type,
                applied = modal.applied,
                onUiEvent = { event ->
                    dismiss()
                    onUiEvent(event)
                }
            )
        }
    }
}