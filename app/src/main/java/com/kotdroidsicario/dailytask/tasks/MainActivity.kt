package com.kotdroidsicario.dailytask.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kotdroidsicario.dailytask.DailyTaskApplication
import com.kotdroidsicario.dailytask.R
import com.kotdroidsicario.dailytask.data.source.TasksRepository
import com.kotdroidsicario.dailytask.preferences.DailyTaskPreferences
import com.kotdroidsicario.dailytask.tasks.adapters.TasksAdapter
import com.kotdroidsicario.dailytask.util.navigateTo
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val tasksViewModel by viewModels<TasksViewModel> {
        TasksViewModelFactory(
            (applicationContext as DailyTaskApplication).taskRepository as TasksRepository,
            DailyTaskPreferences(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeUi()
    }

    fun subscribeUi() {
        tasksViewModel.tasksAvailable.observe(this, Observer { tasks ->
            if (tasks.isNotEmpty()) {
                background_image_view.animate().alpha(0.0f)
                val taskAdapter = tasksAdapter { submitList(tasks) }
                val animatedAdapter = ScaleInAnimationAdapter(taskAdapter)
                val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(taskAdapter))
                itemTouchHelper.attachToRecyclerView(tasks_recycler_view)
                tasks_recycler_view.adapter = animatedAdapter
            } else {
                background_image_view.animate().alpha(1.0f)
                tasks_recycler_view.animate().alpha(0.0f)
            }
        })
    }

    private inline fun tasksAdapter(block: TasksAdapter.() -> Unit): TasksAdapter {
        return TasksAdapter(deleteOperation = { task -> tasksViewModel.delete(task) })
            .apply { block() }
    }

    fun navigateToAddNewTask(view: View) = navigateTo<AddTasksActivity>()

    fun showDialog(){
        MaterialAlertDialogBuilder(this)
            .setTitle("Hello there...")
            .setMessage("Do you mind if i could get to know your name !?")
            .setView(R.layout.custom_view)
            .setNeutralButton("Later") { dialog, which ->
                // Respond to neutral button press
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // Respond to negative button press
            }
            .setPositiveButton("It's Okay") { dialog, which ->
                // Respond to positive button press
            }
            .show()
    }
}