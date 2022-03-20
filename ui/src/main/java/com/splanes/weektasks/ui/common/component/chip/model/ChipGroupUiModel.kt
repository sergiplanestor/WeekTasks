package com.splanes.weektasks.ui.common.component.chip.model

data class ChipGroupUiModel(
    val chips: List<ChipUiModel>,
    val align: ChipAlign = ChipAlign.HorizontalWithScroll,
    val selection: ChipSelection = ChipSelection.Single,
    val onSelectionChange: (ChipUiModel) -> Unit
)

sealed class ChipAlign {
    object Vertical : ChipAlign()
    object Horizontal : ChipAlign()
    object VerticalWithScroll : ChipAlign()
    object HorizontalWithScroll : ChipAlign()
    data class Grid(val rowSpan: Int) : ChipAlign()

}

enum class ChipSelection {
    Single,
    Multiple
}