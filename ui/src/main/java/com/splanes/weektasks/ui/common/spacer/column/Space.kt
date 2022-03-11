package com.splanes.weektasks.ui.common.spacer.column

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.splanes.toolkit.compose.ui.theme.utils.size.UiSize
import com.splanes.weektasks.ui.common.spacer.VerticalSpace
import com.splanes.weektasks.ui.common.utils.dp

@Composable
fun ColumnScope.Weight(value: Double = 1.0, fill: Boolean = true) {
    Spacer(modifier = Modifier.weight(value.toFloat(), fill))
}

@Composable
fun ColumnScope.Space(dp: Dp) {
    VerticalSpace(dp)
}

@Composable
fun ColumnScope.Space(value: Int) {
    Space(value.dp)
}

@Composable
fun ColumnScope.Space(value: Double) {
    Space(value.dp)
}

@Composable
fun ColumnScope.Space(isComponent: Boolean = true, resolver: UiSize.Extended<Dp>.() -> Dp) {
    Space(dp(isComponent, resolver))
}