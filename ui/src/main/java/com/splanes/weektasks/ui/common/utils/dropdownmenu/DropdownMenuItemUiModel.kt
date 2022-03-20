package com.splanes.weektasks.ui.common.utils.dropdownmenu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MenuDefaults
import androidx.compose.ui.Modifier

data class DropdownMenuItemUiModel(
    val id: Int,
    val modifier: Modifier = Modifier,
    val contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
    val onClick: () -> Unit,
)
