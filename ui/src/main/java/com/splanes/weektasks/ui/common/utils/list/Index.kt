package com.splanes.weektasks.ui.common.utils.list

const val INDEX_UNINITIALIZED = -1
const val MIN_INDEX = 0

fun <E> List<E>.isIndexValid(index: Int): Boolean =
    index in MIN_INDEX until size

fun <E> List<E>.safeIndex(index: Int?): Int =
    when {
        index != null && isNotEmpty() -> index.coerceAtLeast(MIN_INDEX).coerceAtMost(lastIndex)
        else -> MIN_INDEX
    }

fun <E, T> List<E>.withValidIndex(index: Int, block: (index: Int) -> T): T? =
    index.takeIf(::isIndexValid)?.run(block)
