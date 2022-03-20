package com.splanes.weektasks.ui.feature.dashboard.contextmenu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.splanes.weektasks.ui.common.utils.resources.Drawables
import com.splanes.weektasks.ui.common.utils.resources.Strings

sealed class DashboardContextMenuItemUiModel(
    open val id: String,
    @StringRes open val text: Int,
    @DrawableRes open val icon: Int,
    open val isSelected: Boolean,
    open val onClick: () -> Unit
)

data class Filter(
    override val isSelected: Boolean = false,
    override val onClick: () -> Unit
) : DashboardContextMenuItemUiModel(
    id = "ContextMenuItem.Filter",
    text = Strings.task_context_menu_filter,
    icon = Drawables.ic_filter,
    isSelected = isSelected,
    onClick = onClick
)

data class Sort(
    override val isSelected: Boolean = false,
    override val onClick: () -> Unit
) : DashboardContextMenuItemUiModel(
    id = "ContextMenuItem.Sort",
    text = Strings.task_context_menu_sort,
    icon = Drawables.ic_sort,
    isSelected = isSelected,
    onClick = onClick
)