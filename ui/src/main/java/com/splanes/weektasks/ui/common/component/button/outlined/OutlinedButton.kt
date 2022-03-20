package com.splanes.weektasks.ui.common.component.button.outlined

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.weektasks.ui.common.component.button.ButtonContent
import com.splanes.weektasks.ui.common.utils.resources.body
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.resources.string

@Composable
fun OutlinedButton(
    @StringRes text: () -> Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: ThemeColorScheme.() -> Color = { primary },
    buttonColors: ButtonColors = buttonOutlinedColors(contentColor = color),
    border: BorderStroke = buttonOutlinedBorder(borderColor = color),
    textStyle: TextStyle = body { large },
    textColor: ThemeColorScheme.() -> Color = { primary },
    content: @Composable RowScope.() -> Unit = {
        ButtonContent.Text(
            text = text,
            textStyle = textStyle,
            textColor = textColor
        )
    }
) {
    OutlinedButton(
        modifier = modifier,
        text = string { text() },
        textColor = textColor,
        textStyle = textStyle,
        buttonColors = buttonColors,
        border = border,
        onClick = onClick,
        content = content
    )
}

@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: ThemeColorScheme.() -> Color = { primary },
    buttonColors: ButtonColors = buttonOutlinedColors(contentColor = color),
    border: BorderStroke = buttonOutlinedBorder(borderColor = color),
    textStyle: TextStyle = body { large },
    textColor: ThemeColorScheme.() -> Color = { primary },
    content: @Composable RowScope.() -> Unit = {
        ButtonContent.Text(
            text = text,
            textStyle = textStyle,
            textColor = textColor
        )
    }
) {
    OutlinedButton(
        modifier = modifier,
        colors = buttonColors,
        border = border,
        onClick = onClick,
        content = content
    )
}

@Composable
fun buttonOutlinedBorder(width: Double = 1.0, borderColor: ThemeColorScheme.() -> Color) =
    BorderStroke(width = width.dp, color = color(block = borderColor).alpha(.8))

@Composable
fun buttonOutlinedColors(contentColor: ThemeColorScheme.() -> Color) =
    with(color(block = contentColor)) {
        ButtonDefaults.outlinedButtonColors(
            contentColor = this,
            disabledContentColor = alpha(0.38)
        )
    }