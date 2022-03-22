package com.splanes.weektasks.ui.feature.sort.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipColors
import androidx.compose.material.ChipDefaults.chipColors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.weektasks.ui.common.component.spacer.column.Space
import com.splanes.weektasks.ui.common.component.spacer.row.Weight
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.resources.dp
import com.splanes.weektasks.ui.common.utils.resources.headline
import com.splanes.weektasks.ui.common.utils.resources.plus
import com.splanes.weektasks.ui.common.utils.resources.title
import com.splanes.weektasks.ui.feature.sort.model.TaskSort
import com.splanes.weektasks.utils.kotlinext.scope.applyIf

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun TaskSortPicker(
    onSelect: (TaskSort) -> Unit,
    modifier: Modifier = Modifier,
    selected: TaskSort? = null
) {
    val types = listOf("Title" to TaskSort.Title(), "Priority" to TaskSort.Priority())
    val orders = listOf("Ascending" to false, "Descending" to true)

    Column(modifier = Modifier.padding(horizontal = dp { small })) {
        Text(
            text = "Select criteria",
            style = headline { small }.copy(fontSize = title { large }.fontSize),
            color = color { onSurface }
        )
        Space { mediumSmall }
        TaskSortChips(
            modifier = modifier,
            items = types,
            isSelected = { selected?.id == it.id },
            onClick = { onSelect(it.applyIf(selected != null) { clone(selected?.desc!!) }) }
        )
        Space { medium }
        Text(
            text = "Select order",
            style = headline { small }.copy(fontSize = title { large }.fontSize),
            color = color { onSurface }
        )
        Space { mediumSmall }
        TaskSortChips(
            modifier = modifier,
            enabled = selected != null,
            items = orders,
            isSelected = { selected?.desc == it },
            onClick = { desc -> selected?.clone(desc)?.run(onSelect) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun <T> TaskSortChips(
    modifier: Modifier,
    enabled: Boolean = true,
    items: List<Pair<String, T>>,
    isSelected: (T) -> Boolean,
    onClick: (T) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Weight()
        items.forEach { (chipText, value) ->
            Chip(
                enabled = enabled,
                border = chipBorder(isSelected(value)),
                colors = chipColors(isSelected(value)),
                onClick = { onClick(value) }
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = dp { tiny },
                        vertical = dp { smallTiny }
                    ),
                    text = chipText,
                    style = title { medium }.run { copy(fontSize = fontSize + 3) },
                    color = chipTextColor(isSelected(value))
                )
            }
            Weight(value = .75)
        }
    }
}

@Composable
private fun chipBorder(isSelected: Boolean): BorderStroke? = if (!isSelected) {
    BorderStroke(1.5.dp, color = color { tertiary.composite(surface.alpha(.5), .9) })
} else {
    null
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun chipColors(isSelected: Boolean): ChipColors =
    chipColors(
        backgroundColor = if (isSelected) {
            color { tertiaryContainer }.alpha(.7)
        } else {
            Color.Transparent
        },
        disabledBackgroundColor = color { surface.alpha(.4) },
    )

@Composable
private fun chipTextColor(isSelected: Boolean): Color = color {
    if (isSelected) onTertiaryContainer else onSurface
}