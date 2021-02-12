package com.kotdroidsicario.dailytask.tasks

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kotdroidsicario.dailytask.DailyTaskApplication
import com.kotdroidsicario.dailytask.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksViewModel(private val application: Application): ViewModel()  {
    private val tasksRepository = (application as DailyTaskApplication).taskRepository

    val tasksAvailable = tasksRepository.observeTasks().asLiveData()

    fun insert(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.insertTask(task)
        }
    }

    fun delete(task:Task){
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.deleteTask(task)
        }
    }

    fun deleteAllTasks(){
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.deleteAllTasks()
        }
    }
}