package com.hardiktrivedi.customtestsuitexample

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import com.hardiktrivedi.customtestsuitexample.annotation.SmokeTest
import com.hardiktrivedi.customtestsuitexample.annotation.ButtonTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        activityRule.launchActivity(null)
    }

    @Test
    @LargeTest
    @ButtonTest
    @SmokeTest
    fun displaysSafetyTip() {
        Espresso.onView(ViewMatchers.withId(R.id.messageTextView)).check(
            ViewAssertions.matches(
                ViewMatchers.withText("Only go outside for food, health reasons or work (but only if you cannot work from home)\n\nIf you go out, stay 2 metres (6ft) away from other people at all times\n\nWash your hands as soon as you get home\n\nAvoid touching your eyes, nose and mouth\n\nClean and disinfect household surfaces\n\nCover your coughs and sneezes")
            )
        )
    }

    @Test
    @SmallTest
    @SmokeTest
    fun displaysTitle() {
        Espresso.onView(ViewMatchers.withId(R.id.titleTextView)).check(
            ViewAssertions.matches(
                ViewMatchers.withText("Coronavirus safety tip")
            )
        )
    }

    @Test
    @MediumTest
    @ButtonTest
    fun displaysIPledgeButton() {
        Espresso.onView(ViewMatchers.withId(R.id.pledgeButton)).check(
            ViewAssertions.matches(
                ViewMatchers.withText("I take pledge to stay at home")
            )
        )
    }
}