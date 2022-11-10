package tests;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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
import functions.ClaimsFunc;
import functions.CommentFunc;
import functions.ControlPanelFunc;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AllureAndroidJUnit4.class)
public class CommentCreationTests {

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

    @Test // тест проходит, но падает при запуске всех тестов
    @DisplayName("Добавление нового комментария заявки с введением валидных данных")
    public void shouldCreateCommentWithValidData() throws InterruptedException {
        String comment = "QA Midgdsr1";
        ControlPanelFunc.goToClaimsBlock();
        ClaimsFunc.goToFirstClaimFromClaimsBlock();
        Thread.sleep(3000);
        ClaimFunc.scrollToLastComment();
        ClaimFunc.initiateCommentCreation();
        CommentFunc.fillInTheCommentField(comment);
        CommentFunc.saveComment();
        Thread.sleep(3000);
        ClaimFunc.scrollToLastComment();
        ClaimFunc.isCommentDisplayed(comment);
    }

    @Test // нестабильный тест при запуске всех тестов в эмуляторе (отдельно проходит)
    @DisplayName("Пустой ввод при добавлении нового комментария к заявке")
    public void shouldTryCreateCommentWithEmptyField() throws InterruptedException {
        ControlPanelFunc.goToClaimsBlock();
        ClaimsFunc.goToFirstClaimFromClaimsBlock();
        Thread.sleep(3000);
        ClaimFunc.scrollToLastComment();
        ClaimFunc.initiateCommentCreation();
        CommentFunc.saveComment();
        CommentFunc.checkMessageThatFieldShouldBeFilled(activityTestRule);
    }

    @Test  // нестабильный тест при запуске всех тестов на эмуляторе (отдельно проходит)
    @DisplayName("Отмена добавления нового комментария")
    public void shouldCancelCommentCreation() throws InterruptedException {
        String comment = "QA Midfrr1";
        ControlPanelFunc.goToClaimsBlock();
        ClaimsFunc.goToFirstClaimFromClaimsBlock();
        Thread.sleep(3000);
        ClaimFunc.scrollToLastComment();
        ClaimFunc.initiateCommentCreation();
        CommentFunc.fillInTheCommentField(comment);
        CommentFunc.cancelCommentCreation();
        Thread.sleep(3000);
        ClaimFunc.scrollToLastComment();
        ClaimFunc.commentDoesNotExist(comment);
    }

}