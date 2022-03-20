package com.splanes.weektasks.ui.common.component.checkbox

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.weektasks.ui.common.utils.modifier.general.alpha
import com.splanes.weektasks.ui.common.utils.post.post
import com.splanes.weektasks.ui.common.utils.resources.Drawables
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.resources.painter
import com.splanes.weektasks.ui.common.utils.ripple.Ripple.Companion.ripple
import com.splanes.weektasks.ui.common.utils.ripple.WithRipple

@Composable
fun CircularCheckBox(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckChange: (isChecked: Boolean) -> Unit = {},
    enabled: Boolean = true,
    size: Dp = 24.dp
) {
    var checked by remember { mutableStateOf(isChecked) }

    val icon = painter { if (checked) Drawables.ic_check_circle else Drawables.ic_circle }
    val tint by animateColorAsState(
        targetValue = color {
            if (checked) success.composite(inverseSurface, .65) else onSurface.alpha(.65)
        },
        animationSpec = tween(durationMillis = 500)
    )
    val rippleColor: ThemeColorScheme.() -> Color = {
        if (checked) {
            onSurface
        } else {
            successContainer.composite(inverseSurface, .85)
        }
    }

    WithRipple(
        rippleColor = rippleColor,
        rippleAlpha =  color { rippleColor() }.ripple().alpha.let {
            if (!checked) {
                it.copy(alphaOnPressed = it.alphaOnPressed + 0.5f)
            } else {
                it
            }
        }
    ) {
        IconButton(
            modifier = modifier,
            onClick = {
                post(delay = 100) {
                    checked = !checked
                    onCheckChange(checked)
                }
            },
            enabled = enabled
        ) {
            Icon(
                modifier = Modifier
                    .size(size)
                    .alpha(.75),
                painter = icon,
                tint = tint,
                contentDescription = "Checkbox"
            )
        }
    }
}

private data class RippleCustomTheme(val isChecked: Boolean, val contentColor: Color) :
    RippleTheme {
    @Composable
    override fun defaultColor() = color {
        if (isChecked) {
            tertiaryContainer.composite(inverseSurface.alpha(.7), 1.0)
        } else {
            onSurface
        }
    }

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleTheme.defaultRippleAlpha(contentColor, !isSystemInDarkTheme())
}