package com.kotdroidsicario.dailytask.tasks

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.kotdroidsicario.dailytask.DailyTaskApplication
import com.kotdroidsicario.dailytask.R
import com.kotdroidsicario.dailytask.data.Task
import com.kotdroidsicario.dailytask.data.source.TasksRepository
import com.kotdroidsicario.dailytask.preferences.DailyTaskPreferences
import com.kotdroidsicario.dailytask.util.navigateTo
import com.kotdroidsicario.dailytask.util.validateTime
import kotlinx.android.synthetic.main.activity_add_tasks.*
import timber.log.Timber

class AddTasksActivity : AppCompatActivity() {
    private val TAG = "AddTasks"

    private val tasksViewModel by viewModels<TasksViewModel> {
        TasksViewModelFactory(
            (applicationContext as DailyTaskApplication).taskRepository as TasksRepository,
            DailyTaskPreferences(applicationContext)
        )
    }

    private var hours: Int = 0
    private var minutes: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tasks)
        val toolbar = findViewById<Toolbar>(R.id.add_task_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        start_time_text_view.setOnClickListener {
            initTimePicker(it as TextView)
        }
    }

    fun initTimePicker(textView: TextView) {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(0)
                .setMinute(0)
                .setTitleText("Select Time")
                .build()

        picker.show(this.supportFragmentManager, TAG)
        observeTimePicker(picker, textView)
    }

    fun observeTimePicker(
        picker: MaterialTimePicker,
        textView: TextView
    ) {
        picker.run {
            addOnPositiveButtonClickListener {
                hours = picker.hour
                minutes = picker.minute
                textView.setText("${hours.validateTime()}:${minutes.validateTime()}")
            }

            addOnNegativeButtonClickListener {

            }

            addOnCancelListener {

            }

            addOnDismissListener {

            }
        }
    }

    fun saveTask(view: View) {
        val taskTitle = validateTaskTitle(task_title_edit_text.text.toString())
        val taskDescription = task_description_edit_text.text.toString()

        Timber.tag(TAG).d("${"%02d".format(hours)}:${"%02d".format(minutes)}")
        val task = Task(
            title = taskTitle,
            description = taskDescription,
            hours = hours,
            minutes = minutes
        )

        tasksViewModel.insert(task)

        task_title_edit_text.setText("")
        task_description_edit_text.setText("")

        navigateTo<MainActivity>()
        finish()
    }

    fun validateTaskTitle(text: String): String = if (text.isEmpty()) "Anonymous Task" else text


}