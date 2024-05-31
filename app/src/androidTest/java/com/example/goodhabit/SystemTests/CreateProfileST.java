package com.example.goodhabit.SystemTests;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import comp3350.goodhabits.Presentation.HomeActivity;
import comp3350.goodhabits.Presentation.ProfileInputActivity;
import comp3350.goodhabits.R;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateProfileST {
    @Rule
    public ActivityTestRule<ProfileInputActivity> activityTestRule = new ActivityTestRule<>(ProfileInputActivity.class);

    @Before
    public void setUp(){
        activityTestRule.finishActivity();
        SystemTestUtils.cleanUpProfileDB();
        SystemTestUtils.cleanUpHabitDB();
        activityTestRule.launchActivity(new Intent());
    }

    @Test
    public void successProfileInput(){
        Intents.init();
        onView(withId(R.id.name_input)).perform(typeText("тест"));  // Ввод имени
        onView(withId(R.id.email_input)).perform(typeText("test@gmail.com"));   // Ввод электронного письма
        onView(withId(R.id.submit_profile)).perform(click()); // Нажатие кнопки Готово
        intended(hasComponent(HomeActivity.class.getName()));   // Убедившись, что мы сейчас находимся на главном экране
        Intents.release();
    }

    @Test
    public void failProfileInput(){
        Intents.init();
        onView(withId(R.id.name_input)).perform(typeText("тест"));  // Ввод имени
        onView(withId(R.id.email_input)).perform(typeText(""));   // Оставляя поле электронной почты пустым
        onView(withId(R.id.submit_profile)).perform(click());   // Нажатие кнопки Готово
        onView(withText("готово")).check(matches(isDisplayed())); // Убеждаюсь, что мы все еще находимся на одном экране
        Intents.release();
    }

    @After
    public void tearDown(){
        SystemTestUtils.cleanUpProfileDB();
        SystemTestUtils.cleanUpHabitDB();
    }
}
