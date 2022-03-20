package com.splanes.weektasks.ui.common.utils.resources

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


operator fun Dp.minus(other: Int): Dp = value.minus(other).dp

operator fun Dp.plus(other: Int): Dp = value.plus(other).dp