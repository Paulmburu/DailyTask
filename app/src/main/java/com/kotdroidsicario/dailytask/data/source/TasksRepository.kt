package com.kotdroidsicario.dailytask.data.source

import com.kotdroidsicario.dailytask.data.Task
import com.kotdroidsicario.dailytask.data.source.local.TasksDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class TasksRepository(val tasksDao: TasksDao) : ITasksRepository {

    override fun observeTasks(): Flow<List<Task>>{
        return tasksDao.observeTasks()
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    override suspend fun insertTask(task: Task){
        tasksDao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task){
        tasksDao.deleteTask(task)
    }

    override suspend fun deleteAllTasks(){
        tasksDao.deleteTasks()
    }
}

interface ITasksRepository {
    fun observeTasks(): Flow<List<Task>>
    suspend fun insertTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun deleteAllTasks()
}