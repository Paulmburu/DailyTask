package com.kotdroidsicario.dailytask.data.source

import androidx.lifecycle.MutableLiveData
import com.kotdroidsicario.dailytask.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class FakeAndroidTestRepository : ITasksRepository {
    private var shouldReturnError = false

    var tasksData: LinkedHashMap<String, Task> = LinkedHashMap()

    private val observableTasks = MutableLiveData<List<Task>>()

    override fun observeTasks(): Flow<List<Task>>
            = flow { emit(tasksData.values.toList()) }

    override suspend fun getAllTasks(): List<Task> {
        return tasksData.values.toList()
    }

    override suspend fun insertTask(task: Task) {
        tasksData.put(task.id, task)
    }

    override suspend fun deleteTask(task: Task) {
        tasksData.remove(task.id)
    }

    override suspend fun deleteAllTasks() {
        tasksData.clear()
    }

    suspend fun refreshTasks() {
        observableTasks.value = getAllTasks()
    }

    fun taskExists(task: Task): Boolean =
        if (tasksData.containsKey(task.id) == null) false else true


    fun addTasks(vararg tasks: Task) {
        for (task in tasks) {
            tasksData[task.id] = task
        }
        runBlocking { refreshTasks() }
    }
}