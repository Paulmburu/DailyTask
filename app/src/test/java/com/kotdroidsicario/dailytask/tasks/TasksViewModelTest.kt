package com.kotdroidsicario.dailytask.tasks

import com.google.common.truth.Truth.assertThat
import com.kotdroidsicario.dailytask.BaseViewModelTest
import com.kotdroidsicario.dailytask.data.source.*
import com.kotdroidsicario.dailytask.getOrAwaitValue
import com.kotdroidsicario.dailytask.preferences.FakeTestPreferences
import com.kotdroidsicario.dailytask.preferences.IDailyTaskPreferences
import org.junit.Before
import org.junit.Test

class TasksViewModelTest : BaseViewModelTest() {
    // Subject under test
    private lateinit var tasksViewModel: TasksViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var tasksRepository: FakeTestRepository

    // Use a fake preferences to be injected into the viewmodel
    private lateinit var dailyTaskPreferences: IDailyTaskPreferences

    @Before
    fun setupViewModel() {
        tasksRepository = FakeTestRepository()
        dailyTaskPreferences = FakeTestPreferences()
        tasksViewModel = TasksViewModel(tasksRepository, dailyTaskPreferences)
    }

    @Test
    fun addSomeTasks_tasksDataSourceNotEmpty(){
        tasksRepository.addTasks(task1, task2, task3)

        val tasks = tasksViewModel.tasksAvailable.getOrAwaitValue {  }
        assertThat(tasks).contains(task2)
    }

    @Test
    fun addNewTask_setsNewTask() {
        val newTask = task4
        tasksViewModel.insert(
            newTask
        )
        assertThat(tasksRepository.taskExists(newTask))
    }

}