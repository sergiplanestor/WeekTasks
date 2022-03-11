package com.splanes.weektasks.ui.feature.newtaskform.common.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.weektasks.domain.common.date.calendar
import com.splanes.weektasks.domain.common.date.daysTo
import com.splanes.weektasks.domain.common.date.endOfWeek
import com.splanes.weektasks.domain.common.date.isEndOfWeek
import com.splanes.weektasks.domain.common.date.isNextWeek
import com.splanes.weektasks.domain.common.date.nextWeek
import com.splanes.weektasks.domain.common.date.toCalendar
import com.splanes.weektasks.ui.common.calendar.Calendar
import com.splanes.weektasks.ui.common.spacer.VerticalSpace
import com.splanes.weektasks.ui.common.spacer.column.Space
import com.splanes.weektasks.ui.common.spacer.row.Weight
import com.splanes.weektasks.ui.common.utils.Drawables
import com.splanes.weektasks.ui.common.utils.Plurals
import com.splanes.weektasks.ui.common.utils.Strings
import com.splanes.weektasks.ui.common.utils.body
import com.splanes.weektasks.ui.common.utils.color
import com.splanes.weektasks.ui.common.utils.dp
import com.splanes.weektasks.ui.common.utils.format
import com.splanes.weektasks.ui.common.utils.formatCalendar
import com.splanes.weektasks.ui.common.utils.headline
import com.splanes.weektasks.ui.common.utils.painter
import com.splanes.weektasks.ui.common.utils.resources
import com.splanes.weektasks.ui.common.utils.shape
import com.splanes.weektasks.ui.common.utils.string
import com.splanes.weektasks.ui.common.utils.title
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import java.util.Calendar

@Composable
fun TaskDeadlineFormField(
    modifier: Modifier = Modifier,
    isFocused: Boolean,
    deadline: String?,
    onDeadlineChanged: (String?) -> Unit,
    onFocusChanged: () -> Unit
) {
    val valueMillis = deadline?.toLongOrNull()
    val valueCalendar = valueMillis?.let(::calendar)
    var isCalendarDialogOpen by remember { mutableStateOf(false) }
    val onDialogStateChanged: (Boolean) -> Unit = { isCalendarDialogOpen = it }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = { if (!isFocused) onFocusChanged() }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val iconSize by animateDpAsState(targetValue = if (isFocused) 28.dp else 24.dp)

        Icon(
            modifier = Modifier.size(iconSize),
            painter = painter { Drawables.ic_timer },
            contentDescription = null,
            tint = color(`if` = isFocused) { secondary.alpha(.65) to onSurface.alpha(.5) }
        )

        if (isFocused) {
            val selected = when {
                valueCalendar?.isEndOfWeek() == true -> DeadlinePickerOption.EndWeek.id
                valueCalendar?.isNextWeek() == true -> DeadlinePickerOption.NextWeek.id
                valueCalendar != null -> DeadlinePickerOption.Custom.id
                else -> -1
            }
            TaskDeadlinePicker(
                modifier = Modifier.padding(
                    start = dp { medium },
                    top = dp { small },
                    bottom = dp { small }
                ),
                selected = selected,
                onDeadlineOptionSelectedChanged = {
                    when (DeadlinePickerOption.find(it)) {
                        DeadlinePickerOption.EndWeek -> onDeadlineChanged(calendar().endOfWeek().timeInMillis.toString())
                        DeadlinePickerOption.NextWeek -> onDeadlineChanged(calendar().nextWeek().timeInMillis.toString())
                        DeadlinePickerOption.Custom -> onDialogStateChanged(true)
                        else -> { /* Nothing to do */
                        }
                    }
                }
            )
        } else {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = dp { mediumLarge },
                        top = dp { small },
                        bottom = dp { small }),
                text = placeholderText(valueCalendar),
                style = title { medium }.copy(fontSize = 17.sp),
                color = color { onSurface.composite(surface, alpha = .35) }
            )
        }

        if (isCalendarDialogOpen) {
            var selectedCustom = calendar()
            val calendarState = rememberSelectableCalendarState(
                onSelectionChanged = { selected ->
                    selected.firstOrNull()?.let { selectedCustom = it.toCalendar() }
                }
            )
            AlertDialog(
                onDismissRequest = { onDialogStateChanged(false) },
                title = {
                    Text(
                        modifier = Modifier.padding(bottom = dp { medium }),
                        text = "Select a deadline date",
                        style = headline { small }
                    )
                },
                text = {
                    Column {
                        Space { medium }
                        Calendar(
                            calendarState = calendarState,
                            daySelectionContainerColor = color { tertiary },
                            daySelectionContentColor = color { onTertiary }
                        )
                        Space { medium }
                    }
                },
                buttons = {
                    Row(
                        modifier = Modifier.padding(
                            vertical = dp { mediumSmall },
                            horizontal = dp { medium })
                    ) {
                        TextButton(
                            onClick = { onDialogStateChanged(false) }
                        ) {
                            Text(
                                text = "Cancel",
                                style = body { large },
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Weight()

                        TextButton(
                            onClick = {
                                onDialogStateChanged(false)
                                onDeadlineChanged(selectedCustom.timeInMillis.toString())
                            }
                        ) {
                            Text(
                                text = "Done",
                                style = body { large },
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                },
                backgroundColor = color { surface },
                shape = shape { small }
            )
        }
    }
}

@Composable
fun TaskDeadlinePicker(
    modifier: Modifier,
    selected: Int,
    onDeadlineOptionSelectedChanged: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(DeadlinePickerOption.values()) { index, item ->
            if (index == 0) {
                VerticalSpace { small }
            }
            TaskDeadlinePickerItem(
                isSelected = selected == item.id,
                text = item.text(),
                onItemSelected = { onDeadlineOptionSelectedChanged(item.id) }
            )
            VerticalSpace { small }
        }
    }
}

@Composable
fun TaskDeadlinePickerItem(
    isSelected: Boolean,
    text: String,
    onItemSelected: () -> Unit
) {
    Text(
        modifier = Modifier
            .background(
                color = color(
                    `if` = isSelected,
                    colors = { tertiaryContainer.composite(surface, .6) to Color.Transparent }
                ),
                shape = shape { medium }
            )
            .padding(horizontal = dp { small }, vertical = dp { smallTiny })
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                role = Role.Button,
                onClick = onItemSelected
            ),
        text = text
    )
}

private enum class DeadlinePickerOption(val id: Int) {
    EndWeek(0),
    NextWeek(1),
    Custom(2);

    @Composable
    fun text(): String =
        when (this) {
            EndWeek -> string(
                block = { Strings.task_form_deadline_picker_option_end_week },
                formatCalendar { endOfWeek() }
            )
            NextWeek -> string(
                block = { Strings.task_form_deadline_picker_option_next_week },
                formatCalendar { nextWeek() }
            )
            Custom -> string { Strings.task_form_deadline_picker_option_custom }
        }

    companion object {
        fun find(id: Int): DeadlinePickerOption? = values().find { it.id == id }
    }
}

@Composable
private fun placeholderText(calendar: Calendar?): String =
    if (calendar != null) {
        val date = calendar.format()
        val days = daysTo(calendar)
        resources {
            getQuantityString(
                Plurals.task_form_deadline_field_placeholder_full,
                days,
                date,
                days.toString()
            )
        }
    } else {
        string { Strings.task_form_deadline_field_placeholder_empty }
    }