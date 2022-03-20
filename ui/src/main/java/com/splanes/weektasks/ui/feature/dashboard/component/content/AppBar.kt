package com.splanes.weektasks.ui.feature.dashboard.component.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Adb
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.DriveFileRenameOutline
import androidx.compose.material.icons.rounded.Inventory2
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.components.feature.statusbar.utils.statusBarColors
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Headline
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Typographies
import com.splanes.weektasks.ui.common.component.button.icon.IconButton
import com.splanes.weektasks.ui.common.component.button.icon.IconSize
import com.splanes.weektasks.ui.common.component.spacer.row.Space
import com.splanes.weektasks.ui.common.component.spacer.row.Weight
import com.splanes.weektasks.ui.common.utils.debug.isDebug
import com.splanes.weektasks.ui.common.utils.dropdownmenu.DropdownMenuItemUiModel
import com.splanes.weektasks.ui.common.utils.resources.Strings
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.resources.dp
import com.splanes.weektasks.ui.common.utils.resources.minus
import com.splanes.weektasks.ui.common.utils.resources.string
import com.splanes.weektasks.ui.common.utils.resources.title
import com.splanes.weektasks.utils.kotlinext.scope.applyIf

@Composable
fun AppBar(
    uiController: SystemUiController,
    isEditModeEnabled: Boolean,
    onRemoveSelectedTasks: () -> Unit,
    onDeleteDatabase: () -> Unit,
    onAutoPopulateDatabase: () -> Unit,
    onEditModeChanged: (isEnabled: Boolean) -> Unit,
) {
    uiController.statusBarColors(
        color = color(composite = isEditModeEnabled) { primary },
        transformation = null
    )
    SmallTopAppBar(
        title = { AppBarTitle(isEditModeEnabled) },
        colors = appBarColors(isEditModeEnabled),
        navigationIcon = { if (isEditModeEnabled) AppBarNavigationAction(onEditModeChanged) },
        actions = {
            Space { small }
            AppBarActionEdit(
                isToggled = isEditModeEnabled,
                onClick = {
                    if (isEditModeEnabled) onRemoveSelectedTasks()
                    onEditModeChanged(!isEditModeEnabled)
                }
            )
            Space { small }
            if (isDebug() && !isEditModeEnabled) {
                val items = listOf(
                    debugMenuOption(
                        id = DEBUG_OPTION_POPULATE_DB,
                        onClick = onAutoPopulateDatabase
                    ),
                    debugMenuOption(id = DEBUG_OPTION_DELETE_DB, onClick = onDeleteDatabase)
                )
                AppBarActionDebugMenu(items) { item ->
                    val isPopulate = item.id == DEBUG_OPTION_POPULATE_DB
                    Weight()
                    Space { small }
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterVertically),
                        text = if (isPopulate) "Populate" else "Delete",
                        style = title { medium },
                        maxLines = 1,
                        color = color { if (isPopulate) onSurface else error }
                    )
                    Space { small }
                    Icon(
                        modifier = Modifier.size(
                            IconSize.small().applyIf(isPopulate) { this - 2 }
                        ),
                        imageVector = Icons.Rounded.run { if (isPopulate) Inventory2 else DeleteForever },
                        tint = color { if (isPopulate) onSurface.alpha(.5) else error.alpha(.6) },
                        contentDescription = null
                    )
                    Space { small }
                }
                Space { small }
            }
        }
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
    IconButton(
        icon = Icons.Rounded.Close,
        contentDescription = string { Strings.content_desc_disable_edit_mode_button },
        onClick = { onEditModeChanged(false) }
    )
}

@Composable
private fun AppBarActionEdit(
    isToggled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier.wrapContentSize(),
        icon = if (isToggled) Icons.Rounded.DeleteOutline else Icons.Rounded.DriveFileRenameOutline,
        size = IconSize.small(),
        tint = color {
            onPrimary.applyIf(isToggled) { composite(surface, alpha = 0.85) }
        },
        contentDescription = string {
            if (isToggled) {
                Strings.content_desc_enable_edit_mode_button
            } else {
                Strings.content_desc_remove_selected_tasks
            }
        },
        onClick = onClick
    )
}

@Composable
private fun AppBarActionDebugMenu(
    items: List<DropdownMenuItemUiModel>,
    itemContent: @Composable RowScope.(DropdownMenuItemUiModel) -> Unit
) {
    var isMenuVisible by remember { mutableStateOf(false) }
    Box {
        IconButton(
            modifier = Modifier.wrapContentSize(),
            icon = Icons.Rounded.Adb,
            size = IconSize.small(),
            tint = color { onPrimary },
            onClick = { isMenuVisible = true }
        )
        DropdownMenu(
            modifier = Modifier.wrapContentSize(),
            expanded = isMenuVisible,
            onDismissRequest = { isMenuVisible = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    modifier = item.modifier,
                    contentPadding = item.contentPadding,
                    onClick = item.onClick,
                    content = { itemContent(item) }
                )
            }
        }
    }
}

@Composable
private fun appBarColors(isEditModeEnabled: Boolean): TopAppBarColors =
    TopAppBarDefaults.smallTopAppBarColors(
        containerColor = color(composite = isEditModeEnabled) { primary },
        titleContentColor = color(composite = isEditModeEnabled) { onPrimary },
        navigationIconContentColor = color(composite = isEditModeEnabled) { onPrimary },
        actionIconContentColor = color(composite = isEditModeEnabled) { onPrimary }
    )

@Composable
private fun color(composite: Boolean, color: ThemeColorScheme.() -> Color): Color =
    color(block = { color().applyIf(composite) { composite(surface, alpha = 0.85) } })

private const val DEBUG_OPTION_POPULATE_DB = 0
private const val DEBUG_OPTION_DELETE_DB = 1

@Composable
private fun debugMenuOption(id: Int, onClick: () -> Unit): DropdownMenuItemUiModel =
    DropdownMenuItemUiModel(
        id = id,
        contentPadding = PaddingValues(horizontal = dp { tiny }),
        onClick = onClick
    )