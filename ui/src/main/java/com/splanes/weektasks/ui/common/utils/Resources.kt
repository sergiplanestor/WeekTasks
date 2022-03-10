package com.splanes.weektasks.ui.common.utils

import android.content.res.Resources
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.toolkit.compose.ui.components.common.utils.color.composite
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.toolkit.compose.ui.theme.feature.paddings.ThemePaddingScheme
import com.splanes.toolkit.compose.ui.theme.feature.shapes.ThemeShapeScheme
import com.splanes.toolkit.compose.ui.theme.utils.accessors.*
import com.splanes.toolkit.compose.ui.theme.utils.size.UiSize

typealias Strings = com.splanes.weektasks.ui.R.string
typealias Plurals = com.splanes.weektasks.ui.R.plurals
typealias Drawables = com.splanes.weektasks.ui.R.drawable

@Composable
fun string(block: () -> Int): String =
    stringResource(id = block())

@Composable
fun string(block: () -> Int, vararg args: Any): String =
    stringResource(id = block(), formatArgs = args)

@Composable
fun painter(block: () -> Int): Painter =
    painterResource(id = block())

@Composable
fun <T> resources(block: Resources.() -> T): T =
    LocalContext.current.resources.block()

@Composable
fun color(
    alpha: Double? = null,
    over: (ThemeColorScheme.() -> Color)? = null,
    block: ThemeColorScheme.() -> Color
): Color =
    with(Colors) {
        block().run {
            when {
                over != null && alpha != null -> composite(other = over(), alpha = alpha)
                over != null -> compositeOver(background = over())
                alpha != null -> alpha(alpha)
                else -> this
            }
        }
    }

@Composable
fun color(
    `if`: Boolean,
    colors: ThemeColorScheme.() -> Pair<Color, Color>
): Color =
    Colors.colors().let { (onTrue, onFalse) ->
        if (`if`) {
            onTrue
        } else {
            onFalse
        }
    }

@Composable
fun shape(size: Int): Shape = RoundedCornerShape(size.dp)

@Composable
fun shape(block: UiSize<RoundedCornerShape>.() -> Shape): Shape = Shapes.block()

@Composable
fun dp(isComponent: Boolean = true, block: UiSize.Extended<Dp>.() -> Dp): Dp =
    (if (isComponent) ComponentPaddings else ViewportPaddings).block()

@Composable
fun display(block: UiSize<TextStyle>.() -> TextStyle): TextStyle =
    Typographies.Display.block()

@Composable
fun headline(block: UiSize<TextStyle>.() -> TextStyle): TextStyle =
    Typographies.Headline.block()

@Composable
fun title(block: UiSize<TextStyle>.() -> TextStyle): TextStyle =
    Typographies.Title.block()

@Composable
fun body(block: UiSize<TextStyle>.() -> TextStyle): TextStyle =
    Typographies.Body.block()

@Composable
fun label(block: UiSize<TextStyle>.() -> TextStyle): TextStyle =
    Typographies.Label.block()