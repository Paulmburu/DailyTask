package com.kotdroidsicario.dailytask.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.kotdroidsicario.dailytask.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest  {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

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

    @Test
    fun insertTaskAndGetTask() = runBlockingTest {
        // GIVEN - Insert a task.
        val task = Task(
            title = "Title1",
            description = "Description1",
            hours = 20,
            minutes = 0,
            isComplete = false
        )

        val anonymousTask = Task(
            title = "Anonymous",
            description = "Anonymous",
            hours = 0,
            minutes = 0,
            isComplete = false
        )
        database.taskDao().insertTask(task)

        // WHEN - Get the task from the database.
        var loaded = anonymousTask
        database.taskDao().getTasks().forEach { if(task.id.equals(it.id)) loaded = it }

        // THEN - The loaded data contains the expected values.
        ViewMatchers.assertThat<Task>(loaded, IsNull.notNullValue(Task::class.java))
        ViewMatchers.assertThat(loaded.id, `is`(task.id))
        ViewMatchers.assertThat(loaded.title, `is`(task.title))
        ViewMatchers.assertThat(loaded.description, `is`(task.description))
        ViewMatchers.assertThat(loaded.isComplete, `is`(task.isComplete))
    }
}