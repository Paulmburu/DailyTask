package com.kotdroidsicario.dailytask

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.kotdroidsicario.dailytask.data.source.ITasksRepository
import com.kotdroidsicario.dailytask.data.source.TasksRepository
import com.kotdroidsicario.dailytask.data.source.local.TasksDatabase
import kotlinx.coroutines.runBlocking

object ServiceLocator {
    private val lock = Any()
    private var database: TasksDatabase? = null

    @Volatile
    var tasksRepository: ITasksRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): ITasksRepository {
        synchronized(this) {
            return tasksRepository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): ITasksRepository {
        val database = database ?: createDataBase(context)
        return TasksRepository(database.taskDao())
    }

    private fun createDataBase(context: Context): TasksDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            TasksDatabase::class.java, "tasks-db"
        ).build()
        database = result
        return result
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            tasksRepository = null
        }
    }
}