package com.kotdroidsicario.dailytask.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kotdroidsicario.dailytask.data.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun taskDao(): TasksDao
}

