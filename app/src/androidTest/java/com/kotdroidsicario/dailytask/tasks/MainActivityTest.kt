package com.kotdroidsicario.dailytask.tasks

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.kotdroidsicario.dailytask.R
import com.kotdroidsicario.dailytask.ServiceLocator
import com.kotdroidsicario.dailytask.data.source.FakeAndroidTestRepository
import com.kotdroidsicario.dailytask.data.source.ITasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class MainActivityTest{

    private lateinit var repository: ITasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun clickAddTaskFAB_addNewTask() = runBlockingTest {
        // GIVEN - On the home screen
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Click on the FAB, edit, and save.
        Espresso.onView(withId(R.id.floatingActionButton)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.task_title_edit_text))
            .perform(ViewActions.replaceText("NEW TITLE"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.task_description_edit_text))
            .perform(ViewActions.replaceText("NEW DESCRIPTION"), closeSoftKeyboard())
//        Espresso.onView(withId(R.id.save_task_button))
//            .perform(ViewActions.click())

        // Verify task is displayed on screen in the task list.
        Espresso.onView(ViewMatchers.withText("NEW TITLE"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // Verify previous task is not displayed.
        Espresso.onView(ViewMatchers.withText("TITLE1")).check(ViewAssertions.doesNotExist())
        // Make sure the activity is closed before resetting the db.
        activityScenario.close()

    }

}