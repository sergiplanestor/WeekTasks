package com.splanes.weektasks.ui.feature.dashboard.component.content

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.components.feature.statusbar.utils.statusBarColors
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors
import com.splanes.toolkit.compose.ui.theme.utils.accessors.ComponentPaddings
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Headline
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Typographies
import com.splanes.weektasks.ui.common.utils.Strings
import com.splanes.weektasks.ui.common.utils.string

@Composable
fun AppBar(
    uiController: SystemUiController,
    isEditModeEnabled: Boolean,
    onRemoveSelectedTasks: () -> Unit,
    onEditModeChanged: (isEnabled: Boolean) -> Unit,
) {
    uiController.statusBarColors(
        color = color(
            color = Colors.primary,
            isEditModeEnabled = isEditModeEnabled
        ),
        transformation = null
    )
    SmallTopAppBar(
        title = { AppBarTitle(isEditModeEnabled) },
        colors = appBarColors(isEditModeEnabled),
        navigationIcon = { if (isEditModeEnabled) AppBarNavigationAction(onEditModeChanged) },
        actions = { AppBarActions(isEditModeEnabled, onRemoveSelectedTasks, onEditModeChanged) }
    )
}

@Composable
private fun AppBarTitle(isEditModeEnabled: Boolean) {
    Text(
        text = string { if (isEditModeEnabled) Strings.select else Strings.dashboard },
        style = Typographies.Headline.small
    )
}

@Composable
private fun AppBarNavigationAction(onEditModeChanged: (isEnabled: Boolean) -> Unit) {
    IconButton(onClick = { onEditModeChanged(false) }) {
        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = string { Strings.content_desc_disable_edit_mode_button }
        )
    }
}

@Composable
private fun AppBarActions(
    isEditModeEnabled: Boolean,
    onRemoveSelectedTasks: () -> Unit,
    onEditModeChanged: (isEnabled: Boolean) -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(horizontal = ComponentPaddings.small)
            .wrapContentSize(),
        onClick = {
            if (isEditModeEnabled) {
                onRemoveSelectedTasks()
            } else {
                onEditModeChanged(true)
            }
        }
    ) {
        Icon(
            modifier = Modifier.size(size = 20.dp),
            imageVector = (if (isEditModeEnabled) Icons.Rounded.Delete else Icons.Rounded.Edit),
            tint = color(Colors.onPrimary, isEditModeEnabled),
            contentDescription = string {
                if (isEditModeEnabled) {
                    Strings.content_desc_enable_edit_mode_button
                } else {
                    Strings.content_desc_remove_selected_tasks
                }
            }
        )
    }
}

@Composable
private fun appBarColors(isEditModeEnabled: Boolean): TopAppBarColors =
    TopAppBarDefaults.smallTopAppBarColors(
        containerColor = color(Colors.primary, isEditModeEnabled),
        titleContentColor = color(Colors.onPrimary, isEditModeEnabled),
        navigationIconContentColor = color(Colors.onPrimary, isEditModeEnabled),
        actionIconContentColor = color(Colors.onPrimary, isEditModeEnabled)
    )

@Composable
private fun color(color: Color, isEditModeEnabled: Boolean): Color =
    if (isEditModeEnabled) {
        color.composite(Colors.surface, alpha = 0.85)
    } else {
        color
    }