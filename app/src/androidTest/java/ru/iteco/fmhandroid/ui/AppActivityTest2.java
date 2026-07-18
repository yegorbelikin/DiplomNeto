package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.ViewMatcher.waitDisplayed;

import android.widget.EditText;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.EspressoIdlingResources;
import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)


public class AppActivityTest2 {


    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

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

    public void appActivityTest2() {

        ViewInteraction loginField = onView(
                allOf(
                        isAssignableFrom(EditText.class),
                        isDescendantOfA(withId(R.id.login_text_input_layout))
                )
        );
        loginField.check(matches(isDisplayed()));
        loginField.perform(replaceText(login), closeSoftKeyboard());

        ViewInteraction passwordField = onView(
                allOf(
                        isAssignableFrom(EditText.class),
                        isDescendantOfA(withId(R.id.password_text_input_layout))
                )
        );
        passwordField.check(matches(isDisplayed()));
        passwordField.perform(replaceText(password), closeSoftKeyboard());


        onView(withId(R.id.enter_button)).perform(click());

        onView(isRoot()).perform(waitDisplayed(R.id.container_custom_app_bar_include_on_fragment_news_list, 5000));


        ViewInteraction textView = onView(withText("News"));
        textView.check(matches(isDisplayed()));


//        ViewInteraction textInputEditText = onView(withId(R.id.login_text_input_layout));
//
//        textInputEditText.check(matches(isDisplayed()));
//        textInputEditText.perform(click());
//        onView(isRoot()).perform(waitDisplayed(R.id.login_text_input_layout, 5000));
//        textInputEditText.perform(typeText(login), closeSoftKeyboard());
////
////        ViewInteraction textInputEditText3 = onView(withId(R.id.password_text_input_layout));
////        textInputEditText3.check(matches(isDisplayed()));
////        textInputEditText3.perform(replaceText(password), closeSoftKeyboard());
////
////        ViewInteraction materialButton = onView(withId(R.id.enter_button));
////        materialButton.perform(click());
//
//        ViewInteraction textView = onView(withText("News"));
//        textView.check(matches(isDisplayed()));
//        textView.check(matches(withText("News")));
//    }


    }
}
