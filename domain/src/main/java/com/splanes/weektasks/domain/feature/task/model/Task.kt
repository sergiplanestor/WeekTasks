package com.splanes.weektasks.domain.feature.task.model

abstract class Task {
    abstract val id: String
    abstract val type: Type
    abstract val title: String
    abstract val notes: String?
    abstract val priority: Priority
    abstract val isDone: Boolean
    abstract val deadline: Long
    abstract val reminders: List<Reminder>

    abstract val createdOn: Long
    abstract val updatedOn: Long
    abstract val markDoneOn: Long

    enum class Priority(val weight: Int) {
        Fire(weight = 5),
        High(weight = 4),
        Medium(weight = 3),
        Low(weight = 2),
        Turtle(weight = 1);

        companion object {
            fun from(weight: Int?, default: Priority = Medium): Priority =
                values().associateBy { it.weight }.getOrDefault(weight ?: default.weight, default)
        }
    }

    enum class Type {
        ToDo,
        Daily
    }

    companion object {
        const val NO_DEADLINE = -1
    }
}