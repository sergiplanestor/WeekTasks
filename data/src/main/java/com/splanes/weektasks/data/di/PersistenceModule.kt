package com.splanes.weektasks.data.di

import android.content.Context
import com.splanes.weektasks.data.common.persistance.TasksDatabase
import com.splanes.weektasks.data.feature.task.todotask.persistance.dao.TodoTaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    fun provideTaskDatabase(@ApplicationContext context: Context): TasksDatabase =
        TasksDatabase.instance(context)

    @Provides
    fun provideTodoTaskDao(database: TasksDatabase): TodoTaskDao =
        database.todoTaskDao()
}