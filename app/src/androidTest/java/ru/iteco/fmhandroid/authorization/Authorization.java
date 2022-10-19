package authorization;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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

import ru.iteco.fmhandroid.R;

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

    @Test
    public void logInToTheApp() throws InterruptedException { // падает если авторизирован в приложении
        ViewInteraction mainText = onView(withText("Авторизация")); // создаём ViewInteraction и указываем по id искомый элемент
        mainText.check(matches(isDisplayed())); // проверка что элемент отражается на старнице
        mainText.check(matches(withText("Авторизация"))); // и у этого эемента текс Войти
        Thread.sleep(5500);
//        SystemClock.sleep(3000);
    }

    @Test
    public void logInWithValidData() throws InterruptedException { // падает если авторизирован в приложении
        onView(allOf(withHint("Логин"))).perform(replaceText(validLogin)).check(matches(withText("login2"))); // вводим логин
        onView(allOf(withHint("Пароль"))).perform(replaceText(validPassword)).check(matches(withText("password2"))); // вводим пароль
        closeSoftKeyboard(); // скрываем клавиатуру ввода
        onView(withId(R.id.enter_button)).perform(click()); // кликаем по кнопк входа
        onView(withId(R.id.container_custom_app_bar_include_on_fragment_main)).check(matches(isDisplayed())); // убеждаемся что вошли в приложение, отображается ВХОСПИСЕ
        Thread.sleep(5500);
//        SystemClock.sleep(3000);
    }

    @Test
    public void exitingTheApplication() throws InterruptedException { // работает если приложение открыто
        onView(withId(R.id.authorization_image_button)).perform(click()); // находим иконку для выхода и кликаем по ней
        onView(withText("Выйти")).check(matches(isDisplayed())); // проверяем что всплывает кнопка Выйти
        onView(withText("Выйти")).perform(click()); // кликаем по всплывающей кнопке
        onView(withText("Авторизация")).check(matches(withText("Авторизация"))); // проверяем что отображается страница для входа
        Thread.sleep(8500);
//        SystemClock.sleep(3000);
    }

    @Test
    public void logInWithInValidData() throws InterruptedException { // падает если авторизирован в приложении
        onView(allOf(withHint("Логин"))).perform(replaceText(invalidLogin)).check(matches(withText("5login"))); // вводим логин
        onView(allOf(withHint("Пароль"))).perform(replaceText(invalidPassword)).check(matches(withText("password"))); // вводим пароль
        closeSoftKeyboard(); // скрываем клавиатуру ввода
        onView(withId(R.id.enter_button)).perform(click()); // кликаем по кнопке входа
        onView(withText("Неверный логин или пароль"))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(7500);
//        SystemClock.sleep(3000);
    }

    @Test
    public void logInWithInValidLoginAndValidPassword() throws InterruptedException { // падает если авторизирован в приложении
        onView(allOf(withHint("Логин"))).perform(replaceText(invalidLogin)).check(matches(withText("5login"))); // вводим логин
        onView(allOf(withHint("Пароль"))).perform(replaceText(validPassword)).check(matches(withText("password2"))); // вводим пароль
        closeSoftKeyboard(); // скрываем клавиатуру ввода
        onView(withId(R.id.enter_button)).perform(click()); // кликаем по кнопке входа
        onView(withText("Неверный логин или пароль"))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(7500);
//        SystemClock.sleep(3000);

    }

    @Test
    public void logInWithValidLoginAndInvalidPassword() throws InterruptedException { // падает если авторизирован в приложении
        onView(allOf(withHint("Логин"))).perform(replaceText(validLogin)).check(matches(withText("login2"))); // вводим логин
        onView(allOf(withHint("Пароль"))).perform(replaceText(invalidPassword)).check(matches(withText("password"))); // вводим пароль
        closeSoftKeyboard(); // скрываем клавиатуру ввода
        onView(withId(R.id.enter_button)).perform(click()); // кликаем по кнопке входа
        onView(withText("Неверный логин или пароль"))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(7500);
//        SystemClock.sleep(3000);
    }

    @Test
    public void logInWithEmptyData() throws InterruptedException { // падает если авторизирован в приложении
        onView(withId(R.id.enter_button)).perform(click()); // кликаем по кнопке входа
        onView(withText("Логин и пароль не могут быть пустыми"))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(7500);
//        SystemClock.sleep(3000);
    }

}