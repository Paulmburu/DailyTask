package com.kotdroidsicario.dailytask.data.source

import com.kotdroidsicario.dailytask.data.Task
import com.kotdroidsicario.dailytask.data.source.local.TasksDao
import kotlinx.coroutines.flow.Flow

interface ITasksRepository {
    fun observeTasks(): Flow<List<Task>>
    fun insertTask(task: Task)
    fun deleteTask(task: Task)
    suspend fun deleteAllTasks()
}

class TasksRepository(val tasksDao: TasksDao) : ITasksRepository {

    override fun observeTasks(): Flow<List<Task>>{
        return tasksDao.observeTasks()
    }

    override fun insertTask(task: Task){
        tasksDao.insertTask(task)
    }

    override fun deleteTask(task: Task){
        tasksDao.deleteTask(task)
    }

    override suspend fun deleteAllTasks(){
        tasksDao.deleteTasks()
    }
}