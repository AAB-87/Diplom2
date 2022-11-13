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
import functions.ClaimFunc;
import functions.MainFunc;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AllureAndroidJUnit4.class)
public class MainBlockTests {

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
    @DisplayName("Наличие разделов \"Новости\" и \"Завки\" в главном блоке")
    public void newsAndClaimsPartsShouldBeInMainBLock() {
        MainFunc.checkIfNewsPartitionExists();
        MainFunc.checkIfClaimsPartitionExists();
    }

    @Test
    @DisplayName("Раскрытие новости в главном блоке")
    public void newsCanBeExpandedInMainBlock() {
        MainFunc.expandFirstNewsInMainBlock();
        MainFunc.checkDescriptionOfFirstNews();
    }

    @Test
    @DisplayName("Полнота информации свернутой новости в главном блоке")
    public void shouldBeFullContentOfNotExpandedNewsInMainBlock() {
        MainFunc.checkContentOfNotExpandedFirstNewsInMainBlock();
    }

    @Test
    @DisplayName("Наличие заявок в разделе \"Заявки\" в главном блоке")
    public void claimsShouldBeVisibleInClaimsPartOnMainBlock() throws InterruptedException {
        MainFunc.expandOrHideNewsPart();
        MainFunc.claimTitleIsDisplayedWithSwipe(0);
        MainFunc.claimTitleIsDisplayedWithSwipe(1);
    }

    @Test // нестабильный тест в эмуляторе (отдельно проходит)
    @DisplayName("Полнота информации заявки в главном блоке (в свернутом состоянии)")
    public void shouldBeFullContentOfNotExpandedClaimInMainBlock() throws InterruptedException {
        MainFunc.expandOrHideNewsPart();
        Thread.sleep(3000);
        MainFunc.checkContentOfFirstClaimInMainBlock();
    }

    @Test // нестабильный тест в эмуляторе (отдельно проходит)
    @DisplayName("Переход к заявке в главном блоке")
    public void shouldGoToFirstClaimInMainBlock() throws InterruptedException {
        MainFunc.expandOrHideNewsPart();
        MainFunc.goToFirstClaimFromMainBlock();
        Thread.sleep(3000);
        ClaimFunc.checkBasicElementsOfClaim();
    }
}