package com.splanes.weektasks.ui.common.component.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.weektasks.ui.common.component.button.icon.IconSize
import com.splanes.weektasks.ui.common.utils.resources.body
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.resources.painter
import com.splanes.weektasks.ui.common.utils.resources.string

object ButtonContent {

    @Composable
    fun Text(
        @StringRes text: () -> Int,
        modifier: Modifier = Modifier,
        textStyle: TextStyle = body { large },
        textColor: ThemeColorScheme.() -> Color = { primary },
    ) {
        Text(
            modifier = modifier,
            text = string { text() },
            textStyle = textStyle,
            textColor = textColor
        )
    }

    @Composable
    fun Text(
        text: String,
        modifier: Modifier = Modifier,
        textStyle: TextStyle = body { large },
        textColor: ThemeColorScheme.() -> Color = { primary },
    ) {
        Text(
            modifier = modifier,
            text = text,
            style = textStyle,
            color = color { textColor() }
        )
    }

    @Composable
    fun Icon(
        icon: () -> Int,
        modifier: Modifier = Modifier,
        size: Dp = IconSize.medium(),
        alpha: Double = .8,
        tint: ThemeColorScheme.() -> Color = { primary },
        description: String? = null
    ) {
        androidx.compose.material3.Icon(
            modifier = modifier.size(size),
            painter = painter { icon() },
            tint = color { tint() }.alpha(alpha),
            contentDescription = description
        )
    }

    @Composable
    fun Icon(
        icon: Icons.Rounded.() -> ImageVector,
        modifier: Modifier = Modifier,
        size: Dp = IconSize.medium(),
        alpha: Double = .8,
        tint: ThemeColorScheme.() -> Color = { primary },
        description: String? = null
    ) {
        androidx.compose.material3.Icon(
            modifier = modifier.size(size),
            imageVector = Icons.Rounded.icon(),
            tint = color { tint() }.alpha(alpha),
            contentDescription = description
        )
    }

}

