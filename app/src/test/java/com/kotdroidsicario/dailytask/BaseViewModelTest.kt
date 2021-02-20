package com.kotdroidsicario.dailytask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

abstract class BaseViewModelTest {

    /**
     * A test rule to allow testing coroutines that use the main dispatcher
     */
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    /**
     * Swaps the background executor used by the Architecture Components with a different one which
     * executes each task synchronously.
     **/
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
}