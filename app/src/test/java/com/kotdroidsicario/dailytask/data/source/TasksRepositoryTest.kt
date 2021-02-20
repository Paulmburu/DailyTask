package com.kotdroidsicario.dailytask.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kotdroidsicario.dailytask.BaseTest
import com.kotdroidsicario.dailytask.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class TasksRepositoryTest : BaseTest(){
    /**
     * A test rule to allow testing coroutines that use the main dispatcher
     */
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Class under test
    private lateinit var tasksRepository: TasksRepository

    @Before
    fun createRepository() {
        // Get a reference to the class under test
        tasksRepository = TasksRepository(tasksDao)
    }

    @Test
    fun getTasks_requestsAllTasksFromLocalDataSource() = mainCoroutineRule.runBlockingTest{
        localTasks.forEach {
                task ->
            tasksRepository.insertTask(task)
        }

        // When tasks are requested from the tasks repository
        val tasks = tasksRepository.tasksDao.getTasks()

        // Then task4 is not contained in localtasks
        assertThat(tasks).doesNotContain(task4)
    }
}