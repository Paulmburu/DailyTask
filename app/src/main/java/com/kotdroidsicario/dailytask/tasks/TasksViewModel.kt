package com.kotdroidsicario.dailytask.tasks


import androidx.lifecycle.*
import com.kotdroidsicario.dailytask.data.Task
import com.kotdroidsicario.dailytask.data.source.ITasksRepository
import com.kotdroidsicario.dailytask.preferences.DailyTaskPreferences
import com.kotdroidsicario.dailytask.preferences.IDailyTaskPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksViewModel(
    private val tasksRepository: ITasksRepository,
    private val dailyTaskPreferences: IDailyTaskPreferences
) : ViewModel() {

    val tasksAvailable = tasksRepository.observeTasks().asLiveData()

    val username = dailyTaskPreferences.getUsername.asLiveData()
    val firstTimeAppLaunchedStatus = dailyTaskPreferences.firstTimeAppLaunchStatus.asLiveData()

    fun insert(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.insertTask(task)
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.deleteTask(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.deleteAllTasks()
        }
    }

    fun saveNewUsername(username: String) {
        viewModelScope.launch {
            dailyTaskPreferences.saveUsername(username)
        }
    }

    fun updateAppLaunched(firstTimeAppLaunched: Boolean) {
        viewModelScope.launch {
            dailyTaskPreferences.updateFirstTimeAppLaunch(firstTimeAppLaunched)
        }
    }

    fun resetUserDetails() {
        viewModelScope.launch {
            dailyTaskPreferences.resetPrefs()
        }
    }
}

class TasksViewModelFactory(
    private val tasksRepository: ITasksRepository,
    private val dailyTaskPreferences: DailyTaskPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = TasksViewModel(tasksRepository,dailyTaskPreferences) as T
}