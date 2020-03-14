package com.revolut.testapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{


    @get: Rule
    val activityScenario = ActivityScenario.launch(MainActivity::class.java)


    @Test
    fun test_activity_inView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.parentCl)).check(matches(isDisplayed()))
    }





}