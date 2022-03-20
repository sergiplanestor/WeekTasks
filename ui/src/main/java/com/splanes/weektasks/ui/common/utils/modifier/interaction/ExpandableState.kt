package com.splanes.weektasks.ui.common.utils.modifier.interaction

enum class ExpandableState {
    Expanded,
    Collapsed
}

fun expandedState(): ExpandableState = ExpandableState.Expanded

fun collapsedState(): ExpandableState = ExpandableState.Collapsed

operator fun ExpandableState.not(): ExpandableState = when (this) {
    ExpandableState.Expanded -> ExpandableState.Collapsed
    ExpandableState.Collapsed -> ExpandableState.Expanded
}

fun ExpandableState.isExpanded(): Boolean = this == ExpandableState.Expanded
fun ExpandableState.isCollapsed(): Boolean = this == ExpandableState.Collapsed