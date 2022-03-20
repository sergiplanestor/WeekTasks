package com.splanes.weektasks.ui.common.utils.ripple

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.splanes.toolkit.compose.ui.components.common.utils.color.isLighterColor
import com.splanes.toolkit.compose.ui.theme.feature.colors.ThemeColorScheme
import com.splanes.weektasks.ui.common.utils.resources.color
import com.splanes.weektasks.ui.common.utils.ripple.Ripple.Companion.ripple

sealed interface Ripple {

    val alphaOnPressed: Float
    val alphaOnFocused: Float
    val alphaOnDragged: Float
    val alphaOnHovered: Float
    val onPressed: Double get() = alphaOnPressed.toDouble()
    val onFocused: Double get() = alphaOnFocused.toDouble()
    val onDragged: Double get() = alphaOnDragged.toDouble()
    val onHovered: Double get() = alphaOnHovered.toDouble()
    val alpha: Alpha
        get() = Alpha(
            alphaOnPressed,
            alphaOnFocused,
            alphaOnDragged,
            alphaOnHovered
        )

    object HighContrast : Ripple {
        override val alphaOnPressed: Float = 0.24f
        override val alphaOnFocused: Float = 0.24f
        override val alphaOnDragged: Float = 0.16f
        override val alphaOnHovered: Float = 0.08f
    }

    object LowContrast : Ripple {
        override val alphaOnPressed: Float = 0.12f
        override val alphaOnFocused: Float = 0.12f
        override val alphaOnDragged: Float = 0.08f
        override val alphaOnHovered: Float = 0.04f
    }

    object DarkMode : Ripple {
        override val alphaOnPressed: Float = 0.10f
        override val alphaOnFocused: Float = 0.12f
        override val alphaOnDragged: Float = 0.08f
        override val alphaOnHovered: Float = 0.04f
    }

    data class Alpha(
        val alphaOnPressed: Float,
        val alphaOnFocused: Float,
        val alphaOnDragged: Float,
        val alphaOnHovered: Float
    ) {
        constructor(
            alphaOnPressed: Double,
            alphaOnFocused: Double,
            alphaOnDragged: Double,
            alphaOnHovered: Double
        ) : this(
            alphaOnPressed.toFloat(),
            alphaOnFocused.toFloat(),
            alphaOnDragged.toFloat(),
            alphaOnHovered.toFloat()
        )

        @Composable
        fun toRippleAlpha(): RippleAlpha = RippleAlpha(
            pressedAlpha = alphaOnPressed,
            focusedAlpha = alphaOnFocused,
            draggedAlpha = alphaOnDragged,
            hoveredAlpha = alphaOnHovered
        )

        operator fun plus(value: Double): Alpha =
            value.toFloat().let { float ->
                this.copy(
                    alphaOnPressed = alphaOnPressed + float,
                    alphaOnFocused = alphaOnFocused + float,
                    alphaOnDragged = alphaOnDragged + float,
                    alphaOnHovered = alphaOnHovered + float
                )
            }
    }

    companion object {
        @Composable
        fun Color.rippleAlpha(
            pressed: Double? = null,
            focused: Double? = null,
            dragged: Double? = null,
            hovered: Double? = null,
        ): RippleAlpha = rippleAlphaOf(
            isHighContrastOnLightMode = isLighterColor(),
            pressed = pressed,
            focused = focused,
            dragged = dragged,
            hovered = hovered
        )

        @Composable
        fun Color.ripple(): Ripple =
            when {
                isSystemInDarkTheme() -> DarkMode
                isLighterColor() -> HighContrast
                else -> LowContrast
            }

        @Composable
        fun ripple(isHighContrastOnLightMode: Boolean = true): Ripple =
            when {
                isSystemInDarkTheme() -> DarkMode
                else -> if (isHighContrastOnLightMode) HighContrast else LowContrast
            }
    }
}

@Composable
fun rippleAlphaOf(
    pressed: Double,
    focused: Double,
    dragged: Double,
    hovered: Double
): RippleAlpha =
    RippleAlpha(
        pressedAlpha = pressed.toFloat(),
        focusedAlpha = focused.toFloat(),
        draggedAlpha = dragged.toFloat(),
        hoveredAlpha = hovered.toFloat()
    )

@Composable
fun rippleAlphaOf(
    isHighContrastOnLightMode: Boolean = true,
    pressed: Double? = null,
    focused: Double? = null,
    dragged: Double? = null,
    hovered: Double? = null,
): RippleAlpha = with(Ripple.ripple(isHighContrastOnLightMode)) {
    rippleAlphaOf(
        pressed = pressed ?: onPressed,
        focused = focused ?: onFocused,
        dragged = dragged ?: onDragged,
        hovered = hovered ?: onHovered
    )
}

fun rippleThemeOf(color: Color, alpha: RippleAlpha): RippleTheme = object : RippleTheme {
    @Composable
    override fun defaultColor() = color

    @Composable
    override fun rippleAlpha(): RippleAlpha = alpha
}

@Composable
fun WithRipple(
    theme: RippleTheme,
    block: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalRippleTheme provides theme) { block() }
}

@Composable
fun WithRipple(
    color: Color,
    alpha: Ripple.Alpha,
    block: @Composable () -> Unit
) {
    WithRipple(theme = rippleThemeOf(color, alpha.toRippleAlpha()), block = block)
}

@Composable
fun WithRipple(
    rippleColor: ThemeColorScheme.() -> Color,
    rippleAlpha: Ripple.Alpha = color { rippleColor() }.ripple().alpha,
    block: @Composable () -> Unit
) {
    WithRipple(
        color = color { rippleColor() },
        alpha = rippleAlpha,
        block = block
    )
}