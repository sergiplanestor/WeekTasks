package com.splanes.weektasks.data.common.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.splanes.weektasks.data.feature.task.todotask.persistance.dao.TodoTaskDao
import com.splanes.weektasks.data.feature.task.todotask.persistance.entity.TodoTaskEntity

@Database(entities = [TodoTaskEntity::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun todoTaskDao(): TodoTaskDao

    companion object {
        private const val DATABASE_NAME = "WeekTasks.Database"

        @Volatile
        private var instance: TasksDatabase? = null

        fun instance(context: Context): TasksDatabase {
            return instance ?: synchronized(this) {
                instance ?: build(context)
            }
        }

        private fun build(context: Context): TasksDatabase {
            return Room
                .databaseBuilder(context, TasksDatabase::class.java, DATABASE_NAME)
                .build()
                .also { instance = it }
        }
    }
}