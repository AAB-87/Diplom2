package ru.iteco.fmhandroid.authorization;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.os.SystemClock;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.ViewActions;

@RunWith(AndroidJUnit4.class) // чтобы класс запускался как набор тестов

public class Authorization {

    @Rule // указываем какое приложение будем запускать. Cм activity в AndroidManifest.xml
    public ActivityTestRule<ru.iteco.fmhandroid.ui.AppActivity> activityTestRule =
            new ActivityTestRule<>(ru.iteco.fmhandroid.ui.AppActivity.class);

    public static final
    String validLogin = "login2";
    String validPassword = "password2";
    String invalidLogin = "5login";
    String invalidPassword = "password";

    // Общие правила
    // ViewMatcher - находим, определяем элемент в иерархии
    // ViewActions - производим действие с элементом
    // ViewAssertions - проверяем состояние найденного элемента

    @Test
    @DisplayName("Проверяем что приложение запускается и отображается окно для авторизации")
    public void logInToTheApp() {
        onView(isRoot()).perform(ViewActions.waitElement(withText("Авторизация"), 10000)); // ожидаем появление нужного элемента
    }

    @Test
    @DisplayName("Авторизация с валидными данными")
    public void logInWithValidData() { // падает если пользователь авторизирован
        onView(isRoot()).perform(ViewActions.waitElement(allOf(withHint("Логин")), 10000)); // ожидаем появление нужного элемента
        onView(allOf(withHint("Логин"))).perform(replaceText(validLogin)).check(matches(withText("login2"))); // вводим логин в этот элемент
        onView(isRoot()).perform(ViewActions.waitElement(allOf(withHint("Пароль")), 10000)); // ожидаем появление нужного элемента
        onView(allOf(withHint("Пароль"))).perform(replaceText(validPassword)).check(matches(withText("password2"))); // вводим пароль в этот элемент
        closeSoftKeyboard(); // скрываем клавиатуру ввода
        onView(withId(R.id.enter_button)).perform(click()); // кликаем по кнопк входа
        onView(withId(R.id.container_custom_app_bar_include_on_fragment_main)).check(matches(isDisplayed())); // убеждаемся что вошли в приложение, отображается ВХОСПИСЕ
    }

    @Test
    @DisplayName("Выход из аккаунта")
     public void logOutTheApplication() { // работает если пользователь не авторизован
        onView(isRoot()).perform(ViewActions.waitElement(withId(R.id.authorization_image_button), 10000));
        onView(withId(R.id.authorization_image_button)).perform(click()); // находим иконку для выхода и кликаем по ней
        onView(withText("Выйти")).check(matches(isDisplayed())); // проверяем что всплывает кнопка Выйти
        onView(withText("Выйти")).perform(click()); // кликаем по всплывающей кнопке
        onView(withText("Авторизация")).check(matches(withText("Авторизация"))); // проверяем что отображается страница для входа
    }

    @Test
    @DisplayName("Вход с НЕвалидными логин и пароль")
    public void logInWithInValidData() { // падает если пользователь авторизован
        onView(isRoot()).perform(ViewActions.waitElement(allOf(withHint("Логин")), 10000)); // ожидаем появление нужного элемента
        onView(allOf(withHint("Логин"))).perform(replaceText(invalidLogin)).check(matches(withText("5login"))); // вводим логин в этот элемент
        onView(isRoot()).perform(ViewActions.waitElement(allOf(withHint("Пароль")), 10000)); // ожидаем появление нужного элемента
        onView(allOf(withHint("Пароль"))).perform(replaceText(invalidPassword)).check(matches(withText("password"))); // вводим пароль в этот элемент
        closeSoftKeyboard(); // скрываем клавиатуру ввода
        onView(withId(R.id.enter_button)).perform(click()); // кликаем по кнопке входа
        onView(withText("Неверный логин или пароль"))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Вход с НЕвалидными логином и валидным паролем")
    public void logInWithInValidLoginAndValidPassword() { // падает если пользователь авторизован
        onView(isRoot()).perform(ViewActions.waitElement(allOf(withHint("Логин")), 10000)); // ожидаем появление нужного элемента
        onView(allOf(withHint("Логин"))).perform(replaceText(invalidLogin)).check(matches(withText("5login"))); // вводим логин в этот элемент
        onView(isRoot()).perform(ViewActions.waitElement(allOf(withHint("Пароль")), 10000)); // ожидаем появление нужного элемента
        onView(allOf(withHint("Пароль"))).perform(replaceText(validPassword)).check(matches(withText("password2"))); // вводим пароль в этот элемент
        closeSoftKeyboard(); // скрываем клавиатуру ввода
        onView(withId(R.id.enter_button)).perform(click()); // кликаем по кнопке входа
        onView(withText("Неверный логин или пароль")) // дальше магия
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Вход с валидным логином и НЕвалидным паролем")
    public void logInWithValidLoginAndInvalidPassword() { // падает если пользователь авторизован
        onView(isRoot()).perform(ViewActions.waitElement(allOf(withHint("Логин")), 10000)); // ожидаем появление нужного элемента
        onView(allOf(withHint("Логин"))).perform(replaceText(validLogin)).check(matches(withText("login2"))); // вводим логин в этот элемент
        onView(allOf(withHint("Пароль"))).perform(replaceText(invalidPassword)).check(matches(withText("password"))); // вводим пароль в этот элемент
        closeSoftKeyboard(); // скрываем клавиатуру ввода
        onView(withId(R.id.enter_button)).perform(click()); // кликаем по кнопке входа
        onView(withText("Неверный логин или пароль")) // дальше магия
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Вход с пустыми полями")
    public void logInWithEmptyData() { // падает если пользователь авторизован
        onView(withId(R.id.enter_button)).perform(click()); // кликаем по кнопке входа
        onView(withText("Логин и пароль не могут быть пустыми"))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

}