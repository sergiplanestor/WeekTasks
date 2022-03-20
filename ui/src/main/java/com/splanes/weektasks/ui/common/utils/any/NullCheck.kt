package com.splanes.weektasks.ui.common.utils.any

import com.splanes.toolkit.compose.base_arch.logger.logAndError

fun <T> T?.isNotNull(): Boolean =
    this != null

fun <T> T?.isNull(): Boolean =
    this == null

fun <T> T?.force(msg: String? = null): T =
    this ?: logAndError(msg.orEmpty())

fun <T, R> withNotNull(value: T?, error: String = "", block: T.() -> R): R =
    with(value.force(error), block)