package com.splanes.weektasks.ui.common.utils

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeIn
import com.splanes.toolkit.compose.ui.components.feature.navhost.graph.transition.fadeOut

enum class EnterTransitionType {
    FadeIn,
    ExpandIn,
    ExpandHorizontally,
    ExpandVertically
}

enum class ExitTransitionType {
    FadeOut,
    ShrinkOut,
    ShrinkHorizontally,
    ShrinkVertically
}

fun enterTransition(type: EnterTransitionType, duration: Int = 500): EnterTransition =
    when (type) {
        EnterTransitionType.FadeIn -> fadeIn(duration = duration)
        EnterTransitionType.ExpandIn -> expandIn(animationSpec = tween(durationMillis = duration))
        EnterTransitionType.ExpandHorizontally -> expandHorizontally(
            animationSpec = tween(
                durationMillis = duration
            )
        )
        EnterTransitionType.ExpandVertically -> expandVertically(
            animationSpec = tween(
                durationMillis = duration
            )
        )
    }

fun exitTransition(type: ExitTransitionType, duration: Int = 500): ExitTransition =
    when (type) {
        ExitTransitionType.FadeOut -> fadeOut(duration = duration)
        ExitTransitionType.ShrinkOut -> shrinkOut(animationSpec = tween(durationMillis = duration))
        ExitTransitionType.ShrinkHorizontally -> shrinkHorizontally(
            animationSpec = tween(
                durationMillis = duration
            )
        )
        ExitTransitionType.ShrinkVertically -> shrinkVertically(animationSpec = tween(durationMillis = duration))
    }

fun fadeIn(duration: Int = 500): EnterTransition =
    enterTransition(EnterTransitionType.FadeIn, duration)

fun expandIn(duration: Int = 500): EnterTransition =
    enterTransition(EnterTransitionType.ExpandIn, duration)

fun expandHorizontally(duration: Int = 500): EnterTransition =
    enterTransition(EnterTransitionType.ExpandHorizontally, duration)

fun expandVertically(duration: Int = 500): EnterTransition =
    enterTransition(EnterTransitionType.ExpandVertically, duration)

fun fadeOut(duration: Int = 500): ExitTransition =
    exitTransition(ExitTransitionType.FadeOut, duration)

fun shrinkOut(duration: Int = 500): ExitTransition =
    exitTransition(ExitTransitionType.ShrinkOut, duration)

fun shrinkHorizontally(duration: Int = 500): ExitTransition =
    exitTransition(ExitTransitionType.ShrinkHorizontally, duration)

fun shrinkVertically(duration: Int = 500): ExitTransition =
    exitTransition(ExitTransitionType.ShrinkVertically, duration)