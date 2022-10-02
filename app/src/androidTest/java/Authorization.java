
import androidx.test.espresso.ViewInteraction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isFocusable;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;

import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AndroidJUnit4.class) // чтобы класс запускался как набор тестов

public class Authorization {

    @Rule // правила запуска приложения. Cм activity в AndroidManifest.xml
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(ru.iteco.fmhandroid.ui.AppActivity.class);

    public static final
    String validLogin = "login2";
    String validPassword = "password2";

//    @Test
//    public void logInToTheApp() {
//        ViewInteraction mainText = onView(withId(R.id.enter_button)); // создаём ViewInteraction и указываем по id искомый элемент
//        mainText.check(matches(isDisplayed())); // проверка что элемент отражается на старнице
//        mainText.check(matches(withText("Войти"))); // и у этого эемента текс Войти
//    }

    @Test
    public void logInToTheApp() {
        ViewInteraction mainText = onView(withText("Авторизация")); // создаём ViewInteraction и указываем по id искомый элемент
        mainText.check(matches(isDisplayed())); // проверка что элемент отражается на старнице
        mainText.check(matches(withText("Авторизация"))); // и у этого эемента текс Войти
    }

    @Test
    public void logInWithValidData() {
        onView(withId(R.id.login_text_input_layout)).perform(typeText(validLogin));

    }
//    @Test
//    public void logInWithValidPassword() {
//        onView(withId(R.id.password_text_input_layout)).perform(typeText(validPassword));
//    }
//    @Test
//    public void testEmptyInputFields() throws Exception {
//        onView(withId(R.id.login_text_input_layout)).check(matches(allOf(
//                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
//                isFocusable(),
//                isClickable(),
//                withText("")
//        )));
//    }
}