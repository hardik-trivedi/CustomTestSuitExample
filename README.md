# CustomTestSuitExample
**A demo to explain how selected and handpicked instrumented test can be run using custom test suit.**

![App Screenshot](/media/AppScreenshot.png)

# Why custom test suit?
While working on your project, you might come across to a situation where you quickly want to check the status of your application and code, just by running the test cases.E.g. You want to make sure that your entire “My Profile “ feature is not breaking. Your profile feature may consist more than one class. So simply running a test class which contains related test cases is not enough. You need more than that, since your profile section has 3–4 classes involved to represent each user activities.
To demonstrate this scenario we have created a simple application which displays some static data to the user. The application looks like following.

There are total 3 test cases written to verify values for given 3 TextViews. Those test cases are shown in following code snippet
```
fun displaysSafetyTip() {
    Espresso.onView(ViewMatchers.withId(R.id.messageTextView)).check(
        ViewAssertions.matches(
            ViewMatchers.withText("Only go outside for food, health reasons or work (but only if you cannot work from home)\n\nIf you go out, stay 2 metres (6ft) away from other people at all times\n\nWash your hands as soon as you get home\n\nAvoid touching your eyes, nose and mouth\n\nClean and disinfect household surfaces\n\nCover your coughs and sneezes")
        )
    )
}


fun displaysTitle() {
    Espresso.onView(ViewMatchers.withId(R.id.titleTextView)).check(
        ViewAssertions.matches(
            ViewMatchers.withText("Coronavirus safety tip")
        )
    )
}


fun displaysIPledgeButton() {
    Espresso.onView(ViewMatchers.withId(R.id.pledgeButton)).check(
        ViewAssertions.matches(
            ViewMatchers.withText("I take pledge to stay at home")
        )
    )
}
```
Simple! No rocket science so far. Now Android offers some annotations to make your job easy. You can use this annotation on your test class or your test function. You can do following things with those annotations.
```
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    
    @LargeTest
    fun displaysSafetyTip() {
        // Your assertions
    }

    @SmallTest
    fun displaysTitle() {
        // Your assertions
    }

    @MediumTest
    fun displaysIPledgeButton() {
        // Your assertions
    }
}
```
I have highlighted few annotations in above code snippet. Those are some readily available annotations under https://developer.android.com/reference/android/support/test/filters/package-summary

Test Annotation available in framework
Now imagine you want to run only those tests which are marked with @LargeTest annotation. Following gradle command will help you to run only @LargeTest
```
./gradlew connectedAndroidTest - info -Pandroid.testInstrumentationRunnerArguments.size=large
./gradlew connectedAndroidTest - info -Pandroid.testInstrumentationRunnerArguments.size=medium
./gradlew connectedAndroidTest - info -Pandroid.testInstrumentationRunnerArguments.size=small
```
It’s also possible to achieve the same with adb command. The equivalent adb shell command would be as following
```
adb shell am instrument -w -e size small com.hardiktrivedi.customtestsuitexample.test/androidx.test.runner.AndroidJUnitRunner
```

There are very limited parameters which we can use along with am instrument command. i.e. https://developer.android.com/studio/test/command-line#AMOptionsSyntax

# How custom annotations can help you to have more customised filters?
Above annotations are only about filtering the test suit by sizes.But remember we discussed what if I want to run only those tests which are part of “My Profile” features. Let’s imagine you come across to a situation frequently where you want to smoke test entire application or want to check some button style for your application. How would you do that? The @LargeTest, or other annotations can not help you. Also it s not a nice idea to use any of the existing annotations, because we have very specific need.
The solution here would be to create your own annotations. For the demo purpose I have created following two annotations. Create a sub package annotation in your androidTest directory under your main package.
```
package com.hardiktrivedi.customtestsuitexample.annotation
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SmokeTest

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FastTest
And I have modified the test suit as well. I have annotated test cases, which are as following.
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    @LargeTest
    @SmokeTest
    fun displaysSafetyTip() {
        // Your assertions
    }

    @Test
    @SmallTest
    @SmokeTest
    fun displaysTitle() {
        // Your assertions
    }

    @Test
    @MediumTest
    @ButtonTest
    fun displaysIPledgeButton() {
        // Your assertions
    }
}
```
Have a look, all respective test cases are annotated with one or the multiple annotations. @Test is the most critical one as this annotation identifies that particular function is a test case.
Running test suit with custom annotation
Now with the help of gradle task we can apply test filters by annotation. E.g. Below command will only run those test cases which are marked with @SmokeTest
```
./gradlew connectedAndroidTest mergeAndroidReports --info -Pandroid.testInstrumentationRunnerArguments.annotation=com.hardiktrivedi.customtestsuitexample.annotation.SmokeTest
```
The command basically takes annotation name as an argument. You have to provide and absolute package path.

Note: There is no equivalent adb shell command to run filtered test using annotations.