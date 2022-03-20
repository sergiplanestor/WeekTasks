package com.splanes.weektasks.ui.common.component.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.weektasks.ui.common.component.button.outlined.buttonOutlinedBorder
import com.splanes.weektasks.ui.common.component.button.outlined.buttonOutlinedColors
import com.splanes.weektasks.ui.common.component.chip.model.ChipAlign
import com.splanes.weektasks.ui.common.component.chip.model.ChipSelection
import com.splanes.weektasks.ui.common.component.chip.model.ChipUiModel
import com.splanes.weektasks.ui.common.utils.resources.body

@Composable
fun ChipGroup(
    chips: List<ChipUiModel>,
    modifier: Modifier = Modifier,
    align: ChipAlign = ChipAlign.HorizontalWithScroll,
    selection: ChipSelection = ChipSelection.Single,
    onSelectionChange: (ChipUiModel) -> Unit,
    color: ThemeColorScheme.() -> Color = { primary },
    buttonColors: ButtonColors = buttonOutlinedColors(contentColor = color),
    border: BorderStroke = buttonOutlinedBorder(borderColor = color),
    textStyle: TextStyle = body { large },
    textColor: ThemeColorScheme.() -> Color = { primary },
    content: @Composable (ChipUiModel) -> Unit
) {
    when (align) {
        is ChipAlign.Grid -> TODO()
        ChipAlign.Horizontal -> TODO()
        ChipAlign.HorizontalWithScroll -> {
            LazyRow(modifier = modifier.fillMaxWidth()) {
                items(chips) { chip ->
                    content(chip)
                }
            }
        }
        ChipAlign.Vertical -> TODO()
        ChipAlign.VerticalWithScroll -> TODO()
    }
}