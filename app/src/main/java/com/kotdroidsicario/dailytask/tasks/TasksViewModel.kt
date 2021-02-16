package com.kotdroidsicario.dailytask.tasks

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kotdroidsicario.dailytask.data.Task
import com.kotdroidsicario.dailytask.data.source.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksViewModel(private val tasksRepository: TasksRepository): ViewModel()  {

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

class TasksViewModelFactory(
    private val tasksRepository: TasksRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = TasksViewModel(tasksRepository) as T
}