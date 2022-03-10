package com.splanes.weektasks.data.common.repositoryimpl

import timber.log.Timber

abstract class RepositoryImpl {
    protected suspend fun <T> exec(
        errorMapper: suspend Throwable.() -> Throwable = { this },
        block: suspend () -> T
    ): T =
        try {
            block()
        } catch (t: Throwable) {
            throw t.errorMapper().also { Timber.e(it) }
        }
}