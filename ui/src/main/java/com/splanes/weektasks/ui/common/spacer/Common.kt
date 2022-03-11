package com.splanes.weektasks.ui.common.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.theme.utils.size.UiSize
import com.splanes.weektasks.ui.common.utils.dp


@Composable
fun HorizontalSpace(dp: Dp) {
    Spacer(modifier = Modifier.width(dp))
}

@Composable
fun HorizontalSpace(value: Int) {
    HorizontalSpace(value.dp)
}

@Composable
fun HorizontalSpace(value: Double) {
    HorizontalSpace(value.dp)
}

@Composable
fun HorizontalSpace(isComponent: Boolean = true, resolver: UiSize.Extended<Dp>.() -> Dp) {
    HorizontalSpace(dp(isComponent, resolver))
}

@Composable
fun VerticalSpace(dp: Dp) {
    Spacer(modifier = Modifier.height(dp))
}

@Composable
fun VerticalSpace(value: Int) {
    VerticalSpace(value.dp)
}

@Composable
fun VerticalSpace(value: Double) {
    VerticalSpace(value.dp)
}

@Composable
fun VerticalSpace(isComponent: Boolean = true, resolver: UiSize.Extended<Dp>.() -> Dp) {
    VerticalSpace(dp(isComponent, resolver))
}