package com.splanes.weektasks.domain.feature.task.model

sealed class Reminder(
    open val id: String,
    open val date: Long,
    open val scheduledOn: Long,
    open val updatedOn: Long
)

data class Push(
    override val id: String,
    override val date: Long,
    override val scheduledOn: Long,
    override val updatedOn: Long,
    val mode: Mode = Mode.Default
) : Reminder(
    id,
    date,
    scheduledOn,
    updatedOn
) {
    sealed class Mode {
        object Sticky : Mode()
        object Default: Mode()
        data class Looper(val interval: Long): Mode()
    }
}
