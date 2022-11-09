package tests;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import functions.AboutAppFunc;
import functions.AuthorizationFunc;
import functions.BrowserFunc;
import functions.ControlPanelFunc;

@RunWith(AllureAndroidJUnit4.class)


public class AboutAppTest {

    @Rule
    public IntentsTestRule intentsTestRule =
            new IntentsTestRule(ru.iteco.fmhandroid.ui.AppActivity.class);

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
    @DisplayName("Полнота информации раздела \"О приложении\"")
    public void shouldBeFullContentInAboutBlock() {
        ControlPanelFunc.goToAboutBlock();
        AboutAppFunc.checkThatAboutBlockContentIsFull();
    }

    @Test
    @DisplayName("Переход к политике конфиденциальности по ссылке")
    public void shouldGoToPrivacyPolicy() {
        ControlPanelFunc.goToAboutBlock();
        AboutAppFunc.goToPrivacyPolicy();
        BrowserFunc.checkTheSuccessfulTransitionToPrivacyPolicy();
    }

    @Test
    @DisplayName("Переход к пользовательскому соглашению по ссылке")
    public void shouldGoToUserAgreement() {
        ControlPanelFunc.goToAboutBlock();
        AboutAppFunc.goToTermsOfUse();
        BrowserFunc.checkTheSuccessfulTransitionToTermsOfUse();
    }
}