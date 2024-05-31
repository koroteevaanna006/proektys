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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import comp3350.goodhabits.Presentation.ProfileActivity;
import comp3350.goodhabits.Presentation.ProfileInputActivity;
import comp3350.goodhabits.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileScreenST {
    @Rule
    public ActivityTestRule<ProfileInputActivity> activityTestRule = new ActivityTestRule<>(ProfileInputActivity.class);

    @Before
    public void setUp(){
        activityTestRule.finishActivity();
        SystemTestUtils.cleanUpProfileDB();
        SystemTestUtils.cleanUpHabitDB();
        SystemTestUtils.setProfileDB("pes", "pes@gmail.com");  // для обходного ввода профиля
        activityTestRule.launchActivity(new Intent());
    }

    @Test
    public void checkProfile(){
        Intents.init();
        onView(withId(R.id.user_profile_button)).perform(click());   // Нажатие кнопки UserProfile на главном экране
        intended(hasComponent(ProfileActivity.class.getName()));   // Проверяем, находимся ли мы сейчас на экране активности профиля
        onView(withText("Рейтинг")).check(matches(isDisplayed()));  // Проверка наличия текстового представления оценки
        onView(withText("2.0")).check(matches(isDisplayed()));  // Проверка наличия текстового представления с рейтинговым номером
        onView(withText("pes")).check(matches(isDisplayed()));  // Проверка наличия текстового представления имени
        onView(withText("pes@gmail.com")).check(matches(isDisplayed()));  // Проверка наличия текстового представления электронного письма
        Intents.release();
    }

    @After
    public void tearDown(){
        SystemTestUtils.cleanUpProfileDB();
        SystemTestUtils.cleanUpHabitDB();
    }
}
