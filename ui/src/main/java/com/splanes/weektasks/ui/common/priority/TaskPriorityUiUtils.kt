package com.splanes.weektasks.ui.common.priority

import androidx.compose.ui.graphics.Color
import com.splanes.toolkit.compose.ui.theme.default.colors.Palette
import com.splanes.weektasks.domain.feature.task.model.Task
import com.splanes.weektasks.ui.common.utils.Drawables
import com.splanes.weektasks.ui.common.utils.Strings

fun Task.Priority.icon(): Int =
    when (this) {
        Task.Priority.Fire -> Drawables.ic_prior_fire
        Task.Priority.High -> Drawables.ic_prior_high
        Task.Priority.Medium -> Drawables.ic_prior_medium
        Task.Priority.Low -> Drawables.ic_prior_low
        Task.Priority.Turtle -> Drawables.ic_prior_turtle
    }

fun Task.Priority.name(): Int =
    when (this) {
        Task.Priority.Fire -> Strings.priority_fire
        Task.Priority.High -> Strings.priority_high
        Task.Priority.Medium -> Strings.priority_medium
        Task.Priority.Low -> Strings.priority_low
        Task.Priority.Turtle -> Strings.priority_turtle
    }

fun Task.Priority.color(): Color =
    when (this) {
        Task.Priority.Fire -> Palette.Red.MexicanRed
        Task.Priority.High -> Palette.Red.Tosca
        Task.Priority.Medium -> Palette.Orange.Koromiko
        Task.Priority.Low -> Palette.Blue.Royal
        Task.Priority.Turtle -> Palette.Blue.Rackley
    }