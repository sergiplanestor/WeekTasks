package com.splanes.weektasks.ui.feature.dashboard.component.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.splanes.toolkit.compose.ui.components.common.utils.color.alpha
import com.splanes.weektasks.domain.feature.task.todotask.model.TodoTask
import com.splanes.weektasks.ui.common.utils.*

@Composable
fun TaskTodoCard(tasks: List<TodoTask>) {

    var isExpanded by remember { mutableStateOf(false) }
    val onChangeExpandState: (Boolean) -> Unit = { expand -> isExpanded = expand }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = dp { medium }),
        shape = shape(size = 8)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dp { medium })
        ) {

            TaskTodoCardHeader(
                modifier = Modifier.fillMaxWidth(),
                isExpanded = isExpanded,
                onClick = { onChangeExpandState(!isExpanded) }
            )

            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dp { mediumSmall })
            ) {
                items(tasks) { task ->
                    Text(text = task.title, style = title { medium })
                }
            }
        }
    }
}

@Composable
fun TaskTodoCardHeader(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    Row(modifier = modifier) {
        Text(
            text = string { Strings.task_todo },
            style = title { large },
            color = color { primary.alpha(.75) }
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onClick) {
            Icon(
                painter = painter {
                    if (isExpanded) {
                        Drawables.ic_chevron_up
                    } else {
                        Drawables.ic_chevron_down
                    }
                },
                contentDescription = string {
                    if (isExpanded) {
                        Strings.content_desc_collapse_todo_tasks
                    } else {
                        Strings.content_desc_expand_todo_tasks
                    }
                },
                tint = color { onSurface.alpha(.5) }
            )
        }
    }
}