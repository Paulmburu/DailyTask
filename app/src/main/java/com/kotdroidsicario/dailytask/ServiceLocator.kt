package com.kotdroidsicario.dailytask

import android.content.Context
import androidx.room.Room
import com.kotdroidsicario.dailytask.data.source.ITasksRepository
import com.kotdroidsicario.dailytask.data.source.TasksRepository
import com.kotdroidsicario.dailytask.data.source.local.TasksDatabase

object ServiceLocator {

    private var database: TasksDatabase? = null
    @Volatile
    var tasksRepository: TasksRepository? = null

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
}