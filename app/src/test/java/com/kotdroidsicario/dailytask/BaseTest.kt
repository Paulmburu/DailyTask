package com.kotdroidsicario.dailytask

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.kotdroidsicario.dailytask.data.source.local.TasksDao
import com.kotdroidsicario.dailytask.data.source.local.TasksDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith


internal open class BaseTest {
    private lateinit var database: TasksDatabase
    protected lateinit var tasksDao: TasksDao

    @Before
    fun initDb(){
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TasksDatabase::class.java
        ).allowMainThreadQueries().build()

        tasksDao = database.taskDao()
    }

    @After
    fun closeDb() = database.close()
}