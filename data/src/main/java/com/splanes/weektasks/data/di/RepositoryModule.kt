package com.splanes.weektasks.data.di

import com.splanes.weektasks.data.feature.task.todotask.repositoryimpl.TodoTasksRepositoryImpl
import com.splanes.weektasks.domain.feature.task.todotask.repository.TodoTasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTodoTaskRepository(impl: TodoTasksRepositoryImpl): TodoTasksRepository
}