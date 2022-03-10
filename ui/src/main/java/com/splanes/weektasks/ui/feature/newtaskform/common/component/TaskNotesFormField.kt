package com.splanes.weektasks.ui.feature.newtaskform.common.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.weektasks.ui.R
import com.splanes.weektasks.ui.common.utils.*

@Composable
fun TaskNotesFormField(
    modifier: Modifier = Modifier,
    isFocused: Boolean,
    notes: String?,
    onNotesChanged: (String?) -> Unit,
    onFocusChanged: (FocusRequester) -> Unit
) {

    val iconSize by animateDpAsState(targetValue = if (isFocused) 26.dp else 24.dp)
    val textSize by animateFloatAsState(targetValue = if (isFocused) 18f else 16f)
    val focusRequester = remember { FocusRequester() }

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        Icon(
            modifier = Modifier.size(iconSize),
            painter = painter { Drawables.ic_notes },
            contentDescription = null,
            tint =  color { if (isFocused) secondary.alpha(.65) else onSurface.alpha(.5) }
        )

        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(start = dp { smallTiny })
                .focusRequester(focusRequester)
                .onFocusChanged { state -> if (state.hasFocus) onFocusChanged(focusRequester) },
            value = notes.orEmpty(),
            onValueChange = onNotesChanged,
            textStyle = title { medium }.copy(fontSize = textSize.sp),
            colors = notesFieldColors(),
            placeholder = {
                Text(
                    text = string { R.string.task_notes_placeholder },
                    style = title { medium }.copy(fontSize = textSize.sp),
                    color = color { onSurface.composite(surface, alpha = .35) }
                )
            }
        )
    }
}

@Composable
private fun notesFieldColors(): TextFieldColors = TextFieldDefaults.textFieldColors(
    textColor = color { onSurface.alpha(alpha = .75) },
    cursorColor = color { primary.composite(surface, alpha = .5) },
    backgroundColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
)