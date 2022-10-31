package tests;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import functions.AuthorizationFunc;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import functions.ControlPanel;

@RunWith(AllureAndroidJUnit4.class) // чтобы класс запускался как набор тестов

// Общие правила
// ViewMatcher - находим, определяем элемент в иерархии
// ViewActions - производим действие с элементом
// ViewAssertions - проверяем состояние найденного элемента

public class AuthorizationTest {

    @Rule // указываем какое приложение будем запускать. Cм activity в AndroidManifest.xml
    public ActivityTestRule<ru.iteco.fmhandroid.ui.AppActivity> activityTestRule =
            new ActivityTestRule<>(ru.iteco.fmhandroid.ui.AppActivity.class);

    String validLogin = "login2";
    String validPassword = "password2";

    @Before
    public void sleep() throws InterruptedException {
        Thread.sleep(7000);
        try {
            AuthorizationFunc.isAuthorizationScreen();
        } catch (NoMatchingViewException e) {
            ControlPanelSteps.logOut();
        }
    }

    @Test
    @DisplayName("Авторизация с валидными данными")
    public void shouldLogInWithValidData() throws InterruptedException {
        AuthorizationFunc.logIn(validLogin, validPassword);
        ControlPanelSteps.checkTradeMark();
        ControlPanelSteps.logOut();
    }

    @Test
    @DisplayName("Вход с пустыми полями")
    public void shouldTryLogInWithEmptyField() throws InterruptedException {
        AuthorizationFunc.clickSignInButton();
        AuthorizationFunc.checkMessageThatFieldShouldNotBeEmpty(activityTestRule);
    }

    @Test
    @DisplayName("Выход из аккаунта")
    public void shouldLogOut() throws InterruptedException {
        AuthorizationFunc.logIn(validLogin, validPassword);
        ControlPanelSteps.logOut();
        AuthorizationFunc.isAuthorizationScreen();
    }
}