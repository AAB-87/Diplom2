package tests;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import functions.AuthorizationFunc;
import functions.ClaimsFunc;
import functions.ControlPanelFunc;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimsFilteringTests {

    @Rule
    public ActivityTestRule<AppActivity> activityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logIn() throws InterruptedException {
        Thread.sleep(7000);
        try {
            AuthorizationFunc.isAuthorizationScreen();
        } catch (NoMatchingViewException e) {
            return;
        }
        AuthorizationFunc.logIn("login2", "password2");
    }

    @Test  // крайне нестабильный тест - падает по техническим причинам (проблемы со свайпом)
    @DisplayName("Выбран статус \"Открыта\" при фильтрации заявок")
    public void openStatusIsChosenDuringClaimsFiltering() throws InterruptedException {
        ControlPanelFunc.goToClaimsBlock();
        ClaimsFunc.initiateClaimFiltering();
        ClaimsFunc.сhooseOnlyOpenStatusIfOpenAndInProgressStatusesAreChosenInitially();
        Thread.sleep(2000);
        ClaimsFunc.checkThatFirstFiveClaimsHaveOpenStatus();
    }

}