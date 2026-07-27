package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.R;

@RunWith(AllureAndroidJUnit4.class)
public class BaseTest {
    @Before
    public void logoutIfNeeded() {
        try {
// Проверяем, есть ли кнопка выхода (значит мы залогинены)
            onView(withId(R.id.authorization_image_button))
                    .check(matches(isDisplayed()));

            // Если залогинены — выходим
            onView(withId(R.id.authorization_image_button)).perform(click());
            onView(withText("Log out")).perform(click());

            // Ждём возврата на экран логина
            onView(withId(R.id.enter_button))
                    .check(matches(isDisplayed()));
        } catch (Exception e) {
            // Если кнопки нет — значит уже на экране логина
        }
    }

}
