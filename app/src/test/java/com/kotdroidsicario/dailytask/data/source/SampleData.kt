package com.kotdroidsicario.dailytask.data.source

import com.kotdroidsicario.dailytask.data.Task

val task1 = Task(
    title = "Title1",
    description = "Description1",
    hours = 20,
    minutes = 0,
    isComplete = false
)
val task2 = Task(
    title = "Title2",
    description = "Description2",
    hours = 0,
    minutes = 0,
    isComplete = false
)
val task3 = Task(
    title = "Title3",
    description = "Description3",
    hours = 23,
    minutes = 58,
    isComplete = true
)

val task4 = Task(
    title = "Anonymous",
    description = "Anonymous Description",
    hours = 23,
    minutes = 58,
    isComplete = true
)

val localTasks = listOf(task1, task2, task3)