package com.splanes.weektasks.ui.common.utils.list

enum class ListConflictResolution {
    Abort,
    Replace,
    Error;

    companion object {
        private const val LIST_CONFLICT_ERROR_MSG =
            "Impossible to add item: `%1\$s` as unique." + "\n" +
                    "Exists one or more items on list that match the same predicate."

        fun errorMessage(item: String): String =
            String.format(LIST_CONFLICT_ERROR_MSG, item.toString())
    }
}