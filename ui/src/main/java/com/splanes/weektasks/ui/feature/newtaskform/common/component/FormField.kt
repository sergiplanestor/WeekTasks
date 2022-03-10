package com.splanes.weektasks.ui.feature.newtaskform.common.component

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeIn
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeOut
import com.splanes.toolkit.compose.ui.theme.utils.accessors.*
import com.splanes.weektasks.ui.common.utils.Drawables
import com.splanes.weektasks.ui.common.utils.painter
import com.splanes.weektasks.ui.common.utils.string

@Composable
fun TextFieldTitle(
    isVisible: Boolean,
    isFocused: Boolean,
    isError: Boolean,
    @StringRes title: Int?
) {
    title?.let {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
                .padding(bottom = ComponentPaddings.tiny)
        ) {
            Animated(isVisible = isVisible) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = string { title },
                    style = Typographies.Title.small.copy(
                        fontSize = 18.sp,
                        fontWeight = if (isFocused) FontWeight.Medium else FontWeight.Normal,
                        color = Colors
                            .run { if (isError) error else onSurface }
                            .composite(Colors.surface, alpha = if (isFocused) .85 else .65)
                    )
                )
            }
        }
    }
}

@Composable
fun TextFieldClearButton(
    isVisible: Boolean,
    isError: Boolean,
    onClick: () -> Unit
) {
    Animated(isVisible = isVisible) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painter { Drawables.ic_clear_circle },
                tint = Colors
                    .run { if (isError) error else onSurface }
                    .composite(Colors.surface, .85),
                contentDescription = null
            )
        }
    }
}

@Composable
fun TextFieldError(
    isError: Boolean,
    isFocused: Boolean,
    @StringRes error: Int?
) {
    Animated(
        isVisible = isError,
        duration = 250,
        enter = expandVertically(animationSpec = tween(durationMillis = 500)),
        exit = shrinkVertically(animationSpec = tween(durationMillis = 500))
    ) {
        error?.let { error ->
            Text(
                modifier = Modifier.padding(top = ComponentPaddings.smallTiny),
                text = string { error },
                style = Typographies.Body.medium.copy(
                    color = Colors.error.composite(Colors.surface, alpha = if (isFocused) .85 else .65)
                )
            )
        }
    }
}

@Composable
fun textFieldColors(): TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
    cursorColor = Colors.onSurface,
    textColor = Colors.onSurface,
    focusedBorderColor = Colors.onSurface,
    unfocusedBorderColor = Colors.onSurface,
    placeholderColor = Colors.onSurface
)

@Composable
fun Animated(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    duration: Int = 500,
    enter: EnterTransition? = null,
    exit: ExitTransition? = null,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = enter?.plus(fadeIn(duration)) ?: fadeIn(duration),
        exit = exit?.plus(fadeOut(duration)) ?: fadeOut(duration),
        content = content
    )
}