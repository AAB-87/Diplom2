package functions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import androidx.test.rule.ActivityTestRule;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import screenElements.AuthorizationScreen;

public class AuthorizationFunc {

    public static void isAuthorizationScreen() {
        Allure.step("Отображается окно авторизации");
        AuthorizationScreen.authorization.check(matches(isDisplayed()));
    }

    public static void logIn(String login, String password) throws InterruptedException {
        Allure.step("Вход в аккаунт");
        AuthorizationScreen.loginInput.perform(replaceText(login));
        AuthorizationScreen.loginInput.check(matches(withText(login)));
        AuthorizationScreen.passwordInput.perform(replaceText(password));
        AuthorizationScreen.passwordInput.check(matches(withText(password)));
        AuthorizationScreen.signInButton.perform(click());
        Thread.sleep(3500);
    }

    public static void clickSignInButton() {
        Allure.step("Кликнуть на кнопку Войти");
        AuthorizationScreen.signInButton.perform(click());
    }

    public static void checkMessageThatFieldShouldNotBeEmpty(ActivityTestRule<AppActivity> activityTestRule) {
        onView(withText(R.string.empty_login_or_password))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow()
                        .getDecorView())))).check(matches(withText("Логин и пароль не могут быть пустыми")));
    }
}