package com.splanes.weektasks.ui.common.utils.route

import androidx.navigation.NavHostController

sealed class GraphRoute(open val route: String) {

    private object Root: GraphRoute(route = "WeekTasks/")

    object GenericDialog: GraphRoute(route = "${Root}popup/")
    object Dashboard: GraphRoute(route = "${Root}dashboard/")

    sealed class TaskForm(override val route: String) : GraphRoute(route) {
        companion object {
            fun from(isTodo: Boolean): TaskForm = if (isTodo) NewTodoTaskForm else NewDailyTaskForm
        }
    }
    object NewTodoTaskForm: TaskForm(route = "${Dashboard}new-todo/")
    object NewDailyTaskForm: TaskForm(route = "${Dashboard}new-daily/")

    override fun toString(): String = route
}

fun NavHostController.redirect(graphRoute: GraphRoute) {
    navigate(route = graphRoute.route)
}