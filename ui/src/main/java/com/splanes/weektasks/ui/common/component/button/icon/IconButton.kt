package com.splanes.weektasks.ui.common.component.button.icon

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.resources.painter
import androidx.compose.material3.IconButton as MaterialIconButton

sealed class IconSize(open val size: Int) {
    object Tiny : IconSize(size = 18)
    object Small : IconSize(size = 20)
    object Medium : IconSize(size = 24)
    object Large : IconSize(size = 26)
    object Huge : IconSize(size = 32)
    data class Custom(override val size: Int) : IconSize(size)

    companion object {
        @Composable fun tiny(): Dp = Tiny.size.dp
        @Composable fun small(): Dp = Small.size.dp
        @Composable fun medium(): Dp = Medium.size.dp
        @Composable fun large(): Dp = Large.size.dp
        @Composable fun huge(): Dp = Huge.size.dp
    }
}

@Composable
fun IconButton(
    icon: () -> Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = IconSize.medium(),
    tint: (ThemeColorScheme.() -> Color)? = null,
    enabled: Boolean = true,
    contentDescription: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    MaterialIconButton(
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        onClick = onClick
    ) {
        Icon(
            painter = painter { icon() },
            modifier = Modifier.size(size),
            tint = tint?.let { color(block = it) } ?: LocalContentColor.current,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun IconButton(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = IconSize.medium(),
    tint: Color? = null,
    enabled: Boolean = true,
    contentDescription: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    MaterialIconButton(
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        onClick = onClick
    ) {
        Icon(
            painter = icon,
            modifier = Modifier.size(size),
            tint = tint ?: LocalContentColor.current,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun IconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = IconSize.medium(),
    tint: Color? = null,
    enabled: Boolean = true,
    contentDescription: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    MaterialIconButton(
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            modifier = Modifier.size(size),
            tint = tint ?: LocalContentColor.current,
            contentDescription = contentDescription
        )
    }
}