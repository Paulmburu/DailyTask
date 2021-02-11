package com.kotdroidsicario.dailytask.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Long = 0L,
    @NonNull
    val title: String,
    @NonNull
    val description: String,
    @NonNull
    @ColumnInfo(name = "start")
    val startTime: String,
    @NonNull
    @ColumnInfo(name = "stop")
    val endTime:String,
    @NonNull
    val isComplete: Boolean = false
)