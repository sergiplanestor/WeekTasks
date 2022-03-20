package com.splanes.weektasks.ui.common.component.chip.model

data class ChipUiModel(
    val id: String,
    val isSelected: Boolean = false,
    val onClick: () -> Unit
)
