package com.splanes.weektasks.ui.common.component.spacer.row

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.theme.utils.size.UiSize
import com.splanes.weektasks.ui.common.component.spacer.HorizontalSpace
import com.splanes.weektasks.ui.common.utils.resources.dp

@Composable
fun RowScope.Weight(value: Double = 1.0, fill: Boolean = true) {
    Spacer(modifier = Modifier.weight(value.toFloat(), fill))
}

@Composable
fun RowScope.Space(dp: Dp) {
    HorizontalSpace(dp)
}

@Composable
fun RowScope.Space(value: Int) {
    Space(value.dp)
}

@Composable
fun RowScope.Space(value: Double) {
    Space(value.dp)
}

@Composable
fun RowScope.Space(isComponent: Boolean = true, resolver: UiSize.Extended<Dp>.() -> Dp) {
    Space(dp(isComponent, resolver))
}