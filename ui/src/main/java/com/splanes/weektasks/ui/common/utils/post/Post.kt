package com.splanes.weektasks.ui.common.utils.post

import android.os.Handler
import android.os.Looper

fun looper(isOnUi: Boolean): Looper =
    Looper.myLooper().takeIf { !isOnUi } ?: Looper.getMainLooper()

fun post(
    isOnUi: Boolean = true,
    delay: Long = 0L,
    looper: Looper = looper(isOnUi),
    block: () -> Unit
) {
    Handler(looper).run {
        if (delay > 0) {
            postDelayed({ block() }, delay)
        } else {
            post { block() }
        }
    }
}