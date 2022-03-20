package com.splanes.weektasks.ui.common.utils.modifier.interaction

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.weektasks.ui.common.utils.resources.color

val interactionSource: MutableInteractionSource
    @Composable
    get() = remember { MutableInteractionSource() }

@Composable
fun ripple(radius: Dp = Dp.Unspecified, resolver: ThemeColorScheme.() -> Color): Indication =
    rememberRipple(color = color(block = resolver), radius = radius)

fun Modifier.onClick(
    interaction: MutableInteractionSource,
    onClick: () -> Unit,
    indication: Indication? = null,
    enabled: Boolean = true,
    role: Role? = null,
    onClickLabel: String? = null
): Modifier =
    this.then(
        Modifier.clickable(
            indication = indication,
            interactionSource = interaction,
            enabled = enabled,
            role = role,
            onClickLabel = onClickLabel,
            onClick = onClick
        )
    )