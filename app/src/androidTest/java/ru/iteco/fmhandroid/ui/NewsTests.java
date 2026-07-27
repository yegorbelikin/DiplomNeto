package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static ru.iteco.fmhandroid.ui.ViewMatcher.waitDisplayed;


import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.TimeZone;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.EspressoIdlingResources;
import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NewsTests extends BaseTest {

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

    @Before
    public void registerIdlingResources() { //Подключаемся к “счетчику”
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource);

    }

//    @Before
//    public void resetAppState() {
//// Очистка данных приложения
//        InstrumentationRegistry.getInstrumentation()
//                .getUiAutomation()
//                .executeShellCommand("pm clear ru.iteco.fmhandroid");
//    }

//    @Before
//    public void logoutIfNeeded() {
//        try {
//// Проверяем, есть ли кнопка выхода (значит мы залогинены)
//            onView(withId(R.id.authorization_image_button))
//                    .check(matches(isDisplayed()));
//
//            // Если залогинены — выходим
//            onView(withId(R.id.authorization_image_button)).perform(click());
//            onView(withText("Log out")).perform(click());
//
//            // Ждём возврата на экран логина
//            onView(withId(R.id.enter_button))
//                    .check(matches(isDisplayed()));
//        } catch (Exception e) {
//            // Если кнопки нет — значит уже на экране логина
//        }
//    }

    @After
    public void unregisterIdlingResources() { //Отключаемся от “счетчика”
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource);
    }
    String login = "login2";
    String password = "password2";




    @Test
    public void createNewsTest() {
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Yekaterinburg");
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
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

        onView(withId(R.id.all_news_text_view)).perform(click());
        onView(withId(R.id.edit_news_material_button)).perform(click());
        onView(withId(R.id.add_news_image_view)).perform(click());
        onView(withId(R.id.news_item_category_text_auto_complete_text_view)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Массаж")))
                .inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.news_item_title_text_input_edit_text)).perform(click())
                .perform(replaceText("Massage"));

        onView(withId(R.id.news_item_publish_date_text_input_edit_text)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year,month,day));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.news_item_publish_time_text_input_edit_text)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(hour, minute));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.news_item_description_text_input_edit_text)).perform(click())
                .perform(replaceText("Тайский массаж"));
                onView(withId(R.id.save_button)).perform(click());




//        ViewInteraction viewGroup = onView(
//                allOf(withParent(allOf(withId(R.id.news_item_material_card_view),
//                                withParent(withId(R.id.news_list_recycler_view)))),
//                        isDisplayed()));
//        viewGroup.check(matches(isDisplayed()));
//
//        ViewInteraction textView = onView(
//                allOf(withId(R.id.news_item_title_text_view), withText("massage"),
//                        withParent(withParent(withId(R.id.news_item_material_card_view))),
//                        isDisplayed()));
//        textView.check(matches(withText("massage")));
    }}




