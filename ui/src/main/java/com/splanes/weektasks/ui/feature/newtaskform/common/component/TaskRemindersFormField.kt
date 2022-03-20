package com.splanes.weektasks.ui.feature.newtaskform.common.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Colors
import com.splanes.toolkit.compose.ui.theme.utils.accessors.ComponentPaddings
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Title
import com.splanes.toolkit.compose.ui.theme.utils.accessors.Typographies
import com.splanes.weektasks.ui.R
import com.splanes.weektasks.ui.common.utils.resources.Drawables
import com.splanes.weektasks.ui.common.utils.resources.painter
import com.splanes.weektasks.ui.common.utils.resources.string

@Composable
fun TaskRemindersFormField(
    modifier: Modifier = Modifier,
    reminders: String?,
    onRemindersChanged: (String?) -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        var isFocused by remember { mutableStateOf(false) }
        val iconSize by animateDpAsState(targetValue = if (isFocused) 28.dp else 24.dp)
        val textSize by animateFloatAsState(targetValue = if (isFocused) 21f else 17f)

        Icon(
            modifier = Modifier.size(iconSize),
            painter = painter { Drawables.ic_notes },
            contentDescription = null,
            tint = if (isFocused) Colors.secondary.alpha(.65) else Colors.onSurface.alpha(.5)
        )

        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(start = ComponentPaddings.smallTiny)
                .onFocusEvent { state -> isFocused = state.hasFocus },
            value = reminders.orEmpty(),
            onValueChange = onRemindersChanged,
            textStyle = Typographies.Title.medium.copy(fontSize = textSize.sp),
            colors = notesFieldColors(),
            placeholder = {
                Text(
                    text = string { R.string.task_notes_placeholder },
                    style = Typographies.Title.medium.copy(fontSize = textSize.sp),
                    color = Colors.onSurface.composite(Colors.surface, alpha = .35)
                )
            }
        )
    }
}

@Composable
private fun notesFieldColors(): TextFieldColors = TextFieldDefaults.textFieldColors(
    textColor = Colors.onSurface.alpha(alpha = .75),
    cursorColor = Colors.primary.composite(Colors.surface, alpha = .5),
    backgroundColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
)