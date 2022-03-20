package com.splanes.weektasks.ui.common.component.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.weektasks.ui.common.component.spacer.row.Space
import com.splanes.weektasks.ui.common.utils.modifier.interaction.interactionSource
import com.splanes.weektasks.ui.common.utils.modifier.interaction.onClick
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.resources.title
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import java.time.DayOfWeek

@Composable
fun Calendar(
    calendarState: CalendarState<DynamicSelectionState>,
    modifier: Modifier = Modifier,
    daySelectionContainerColor: Color,
    daySelectionContentColor: Color
) {
    SelectableCalendar(
        modifier = modifier,
        calendarState = calendarState,
        firstDayOfWeek = DayOfWeek.MONDAY,
        monthHeader = { state -> MonthHeader(monthState = state) },
        dayContent = { state ->
            DayContent(
                dayState = state,
                daySelectionContainerColor = daySelectionContainerColor,
                daySelectionContentColor = daySelectionContentColor
            )
        }
    )
}

@Composable
fun MonthHeader(
    monthState: MonthState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { monthState.currentMonth = monthState.currentMonth.minusMonths(1) }) {
            Image(
                imageVector = Icons.Default.KeyboardArrowLeft,
                colorFilter = ColorFilter.tint(color { onSurface.alpha(.75) }),
                contentDescription = "Previous",
            )
        }
        Text(
            text = monthState.currentMonth.month.name.lowercase()
                .replaceFirstChar { it.titlecase() },
            style = title { large }
        )
        Space(8)
        Text(
            text = monthState.currentMonth.year.toString(),
            style = title { large }
        )
        IconButton(
            onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) }
        ) {
            Image(
                imageVector = Icons.Default.KeyboardArrowRight,
                colorFilter = ColorFilter.tint(color { onSurface.alpha(.75) }),
                contentDescription = "Next",
            )
        }
    }
}

@Composable
fun DayContent(
    dayState: DayState<DynamicSelectionState>,
    daySelectionContainerColor: Color,
    daySelectionContentColor: Color,
    modifier: Modifier = Modifier,
) {
    val date = dayState.date
    val selectionState = dayState.selectionState
    val isSelected = selectionState.isDateSelected(date)

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .onClick(
                interaction = interactionSource,
                onClick = { selectionState.onDateSelected(date) }
            )
            .background(
                color = color(`if` = isSelected) { daySelectionContainerColor.alpha(.6) to surface },
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            color = color(`if` = isSelected) { daySelectionContentColor to onSurface }.alpha(if (dayState.isFromCurrentMonth) 1.0 else .35)
        )
    }
}