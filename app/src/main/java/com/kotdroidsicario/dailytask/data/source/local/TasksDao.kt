package com.kotdroidsicario.dailytask.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kotdroidsicario.dailytask.data.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Query("SELECT * FROM Tasks")
    fun observeTasks(): Flow<List<Task>>

    @Query("SELECT * FROM Tasks")
    suspend fun getTasks(): List<Task>

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteTasks()

}