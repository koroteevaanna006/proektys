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
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import comp3350.goodhabits.Presentation.AllHabitsActivity;
import comp3350.goodhabits.Presentation.ProfileInputActivity;
import comp3350.goodhabits.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DeleteHabitST {
    @Rule
    public ActivityTestRule<ProfileInputActivity> activityTestRule = new ActivityTestRule<>(ProfileInputActivity.class);

    @Before
    public void setUp(){
        activityTestRule.finishActivity();
        SystemTestUtils.cleanUpProfileDB();
        SystemTestUtils.cleanUpHabitDB();
        SystemTestUtils.setProfileDB("тест", "test@gmail.com");  // для обходного ввода профиля
        activityTestRule.launchActivity(new Intent());
    }

    @Test
    public void deleteHabit(){
        Intents.init();
        onView(withId(R.id.all_habits_button)).perform(click());    // Нажав на кнопку "Все привычки" на главном экране
        intended(hasComponent(AllHabitsActivity.class.getName()));  // Убеждаюсь, что сейчас мы находимся в режиме AllHabitsActivity
        onView(withText("Курение")).check(matches(isDisplayed()));  // Проверяем, есть ли в списке привычка бросать курить
        onView(withText("Пить воду")).check(matches(isDisplayed()));  // Проверяем, есть ли в списке привычка пить воду
        onView(withText("Делать зарядку")).check(matches(isDisplayed()));  // Проверяем, есть ли в списке привычка заниматься йогой
        onView(withText("Пить воду")).perform(longClick());   // При длительном нажатии на привычку пить воду открывается диалоговое окно для удаления привычки
        onView(withText("ДА")).perform(click());   // Нажмите кнопку ДА, чтобы удалить привычку из списка
        onView(withText("Пить воду")).check(doesNotExist());  // Проверка того, сохранилась ли удаленная привычка в списке
        Intents.release();
    }

    @After
    public void tearDown(){
        SystemTestUtils.cleanUpProfileDB();
        SystemTestUtils.cleanUpHabitDB();
    }
}
