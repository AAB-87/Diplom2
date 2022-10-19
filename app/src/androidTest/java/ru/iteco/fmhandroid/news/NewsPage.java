package ru.iteco.fmhandroid.news;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.os.SystemClock;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.NoMatchingViewException;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.authorization.Authorization;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.AppActivity;

//@RunWith(AndroidJUnit4.class) // чтобы класс запускался как набор тестов

//public class NewsPage {
//
//    @Rule // указываем какое приложение будем запускать. Cм activity в AndroidManifest.xml
//    public ActivityTestRule<AppActivity> activityTestRule =
//            new ActivityTestRule<>(ru.iteco.fmhandroid.ui.AppActivity.class);
//
//    @Before
//    public void IsAuthorizationScreenOpen() throws InterruptedException {
//        try {
//            Authorization.logInToTheApp().check(matches(isDisplayed())); // проверяет отображается ли окно авторизации после запуска приложения
//        } catch (NoMatchingViewException e) { // если нет, ловит ошибку кладёт её в ячеёку e (e сокращённо Exception) и программа не "умирает"
//            return;
//        }
//        Authorization.logInWithValidData("login2", "password2"); // если да, то логинится
//        Thread.sleep(5500);
//    }
//
//    public void ViewNews() {
//        onView(withId(R.id.news_list_swipe_refresh)).perform(click());
//        onView(withText("Выйти")).check(matches(isDisplayed()));
//
//    }


//}


