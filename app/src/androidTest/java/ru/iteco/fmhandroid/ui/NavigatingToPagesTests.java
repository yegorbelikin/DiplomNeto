package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import static ru.iteco.fmhandroid.ui.ViewMatcher.waitDisplayed;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.instanceOf;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.EspressoIdlingResources;
import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NavigatingToPagesTests {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);
    @BeforeClass
    public static void disableAnimations() {
        // Принудительно отключаем анимации через ADB shell перед запуском тестов в классе

        InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("settings put global window_animation_scale 0");
        InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("settings put global transition_animation_scale 0");
        InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("settings put global animator_duration_scale 0");
    }
//        @Before
//    public void resetAppState() {
//// Очистка данных приложения
//        InstrumentationRegistry.getInstrumentation()
//                .getUiAutomation()
//                .executeShellCommand("pm clear ru.iteco.fmhandroid");
//    }
    @Before
    public void registerIdlingResources() { //Подключаемся к “счетчику”
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource);

    }





    @After
    public void unregisterIdlingResources() { //Отключаемся от “счетчика”
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource);
    }

    String login = "login2";
    String password = "password2";


    @Test
    public void GoToMissionPage() {

        ViewInteraction loginField = onView(
                allOf(isAssignableFrom(EditText.class),
                        isDescendantOfA(withId(R.id.login_text_input_layout))));
        loginField.check(matches(isDisplayed()));
        loginField.perform(replaceText(login), closeSoftKeyboard());
        ViewInteraction passwordField = onView(
                allOf(isAssignableFrom(EditText.class),
                        isDescendantOfA(withId(R.id.password_text_input_layout))));
        passwordField.check(matches(isDisplayed()));
        passwordField.perform(replaceText(password), closeSoftKeyboard());
        onView(withId(R.id.enter_button)).perform(click());
        onView(isRoot()).perform(waitDisplayed(R.id.main_menu_image_button, 5000));
        onView(withId(R.id.our_mission_image_button)).perform(click());
        ViewInteraction imageButton = onView(withId(R.id.our_mission_title_text_view));
        imageButton.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

    @Test
    public void GoToNewsPage() {
        ViewInteraction loginField = onView(
                allOf(isAssignableFrom(EditText.class),
                        isDescendantOfA(withId(R.id.login_text_input_layout))));
        loginField.check(matches(isDisplayed()));
        loginField.perform(replaceText(login), closeSoftKeyboard());
        ViewInteraction passwordField = onView(
                allOf(isAssignableFrom(EditText.class),
                        isDescendantOfA(withId(R.id.password_text_input_layout))));
        passwordField.check(matches(isDisplayed()));
        passwordField.perform(replaceText(password), closeSoftKeyboard());
        onView(withId(R.id.enter_button)).perform(click());
        onView(isRoot()).perform(waitDisplayed(R.id.main_menu_image_button, 5000));
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onData(anything())
                .inAdapterView(isAssignableFrom(android.widget.AdapterView.class))
                .atPosition(1).perform(click());
        ViewInteraction imageButton = onView(withId(R.id.news_list_swipe_refresh));
        imageButton.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

    @Test
    public void GoToAboutPage() {
        ViewInteraction loginField = onView(
                allOf(isAssignableFrom(EditText.class),
                        isDescendantOfA(withId(R.id.login_text_input_layout))));
        loginField.check(matches(isDisplayed()));
        loginField.perform(replaceText(login), closeSoftKeyboard());
        ViewInteraction passwordField = onView(
                allOf(isAssignableFrom(EditText.class),
                        isDescendantOfA(withId(R.id.password_text_input_layout))));
        passwordField.check(matches(isDisplayed()));
        passwordField.perform(replaceText(password), closeSoftKeyboard());
        onView(withId(R.id.enter_button)).perform(click());
        onView(isRoot()).perform(waitDisplayed(R.id.main_menu_image_button, 5000));
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onData(anything())
                .inAdapterView(isAssignableFrom(android.widget.AdapterView.class))
                .atPosition(2).perform(click());
        ViewInteraction imageButton = onView(withId(R.id.about_version_title_text_view));
        imageButton.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

}
