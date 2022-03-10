package com.splanes.weektasks.ui.feature.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Shapes
import com.splanes.toolkit.compose.ui.theme.utils.accessors.ViewportPaddings
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.ui.feature.dashboard.component.content.AppBar
import com.splanes.weektasks.ui.feature.dashboard.component.content.NewTaskMenu
import com.splanes.weektasks.ui.feature.dashboard.contract.DashboardEvent
import com.splanes.weektasks.ui.feature.newtaskform.common.component.NewTaskForm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DashboardContainer(
    uiController: SystemUiController,
    coroutineScope: CoroutineScope,
    isEditModeEnabled: Boolean,
    onEditModeChanged: (isEnabled: Boolean) -> Unit,
    onUiEvent: (DashboardEvent) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    val scaffoldState = rememberBottomSheetScaffoldState()

    var newTaskModalType: Task.Type? by remember { mutableStateOf(null) }
    val onNewTaskModalStateChanged: (BottomSheetValue) -> Unit = { state ->
        coroutineScope.launch { scaffoldState.bottomSheetState.animateTo(state) }
    }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetContent = {
            newTaskModalType?.let {
                NewTaskForm(
                    type = it,
                    onNewTaskCreated = {
                        onNewTaskModalStateChanged(BottomSheetValue.Collapsed)
                        onUiEvent(DashboardEvent.LoadTasks)
                    }
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
                onRemoveSelectedTasks = { onUiEvent(DashboardEvent.RemoveTasksSelected) }
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
                onNewTaskModal = { type ->
                    newTaskModalType = type
                    onNewTaskModalStateChanged(BottomSheetValue.Expanded)
                }
            )
        }
    }
}