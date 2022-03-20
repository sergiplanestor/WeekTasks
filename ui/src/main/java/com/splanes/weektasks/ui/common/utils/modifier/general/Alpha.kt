package com.splanes.weektasks.ui.common.utils.modifier.general

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha as sdkAlpha

fun Modifier.alpha(alpha: Double): Modifier =
    this.then(Modifier.sdkAlpha(alpha.toFloat()))