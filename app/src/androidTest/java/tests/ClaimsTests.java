package tests;

import androidx.test.espresso.NoMatchingViewException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import functions.AuthorizationFunc;
import functions.ClaimFunc;
import functions.ClaimsFunc;
import functions.ControlPanelFunc;

import androidx.test.rule.ActivityTestRule;

import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimsTests {

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

    @Test
    @DisplayName("Наличие всех заявок в блоке \"Заявки\" (минимум 3)")
    public void shouldBeThreeClaimsInClaimsBlock() {
        ControlPanelFunc.goToClaimsBlock();
        ClaimsFunc.checkThatThereAreThreeClaimsItemsInTheClaimsBlock();
    }

    @Test // тест нестабильный при запуске всех тестов в эмуляторе (отдельно проходит)
    @DisplayName("Полнота информации заявок (в свернутом состоянии) в блоке \"Заявки\"")
    public void shouldBeFullContentOfNotExpandedClaimInClaimsBlock() throws InterruptedException {
        ControlPanelFunc.goToClaimsBlock();
        Thread.sleep(3000);
        ClaimsFunc.checkContentOfFirstClaimInClaimsBlock();
    }

    @Test
    @DisplayName("Полнота информации раскрытой заявки в блоке \"Заявки\"")
    public void shouldBeFullContentOfExpandedClaimInClaimsBlock() throws InterruptedException {
        ControlPanelFunc.goToClaimsBlock();
        ClaimsFunc.goToFirstClaimFromClaimsBlock();
        Thread.sleep(3000);
        ClaimFunc.checkFullContentOfExpandedClaim();
    }

}