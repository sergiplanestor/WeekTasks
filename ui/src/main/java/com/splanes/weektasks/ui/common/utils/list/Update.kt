package com.splanes.weektasks.ui.common.utils.list

import com.splanes.toolkit.compose.base_arch.logger.logAndError
import com.splanes.weektasks.ui.common.utils.list.ListConflictResolution.Companion.errorMessage

fun <E> List<E>.mutate(apply: MutableList<E>.() -> Unit): MutableList<E> =
    (if (this is MutableList) this else toMutableList()).apply(apply)

fun <E> List<E>.removeAll(matcher: (E) -> Boolean): MutableList<E> = mutate {
    filter(matcher).forEach { item -> remove(item) }
}

fun <E> List<E>.replace(old: E, new: E): MutableList<E> = mutate {
    val index = indexOf(old)
    removeAt(index)
    add(index, new)
}

fun <E> List<E>.replace(
    matcher: (E) -> Boolean,
    transformation: (E) -> E
): MutableList<E> = mutate {
    find(matcher)?.run { replace(old = this, new = transformation(this)) }
}

fun <E> List<E>.replaceAll(
    matcher: (E) -> Boolean,
    transformation: (E) -> E
): MutableList<E> = mutate {
    filter(matcher).forEach { item -> replace(old = item, new = transformation(item)) }
}

fun <E> List<E>.addAll(index: Int? = null, items: Collection<E>): MutableList<E> = mutate {
    addAll(safeIndex(index), items)
}

fun <E> List<E>.add(index: Int? = null, item: E): MutableList<E> =
    addAll(index, listOf(item))

fun <E> List<E>.addUnique(
    item: E,
    index: Int? = null,
    onConflict: ListConflictResolution = ListConflictResolution.Abort,
    checker: (E) -> Boolean = { it == item }
): MutableList<E> = mutate {
    when (count(checker)) {
        0 -> addAll(index, listOf(item))
        else -> {
            when (onConflict) {
                ListConflictResolution.Abort -> {
                    // Do nothing
                }
                ListConflictResolution.Replace -> {
                    replaceAll(matcher = checker, transformation = { item })
                }
                ListConflictResolution.Error -> {
                    logAndError(errorMessage(item.toString()))
                }
            }
        }
    }
}
