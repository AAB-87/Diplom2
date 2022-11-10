package tests;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import functions.CommentFunc;

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
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AllureAndroidJUnit4.class)
public class CommentEditingTests {

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

    @Test // проходит на эмуляторе, но падает, при запуске всех тестов
    @DisplayName("Редактирование комментария в заявке при валидных данных (кириллические символы)")
    public void shouldEditCommentOfClaim() throws InterruptedException {
        String comment = "QA Midd1088";
        String commentForEditing = "QA Midd222s8";
        ControlPanelFunc.goToClaimsBlock();
        ClaimsFunc.goToFirstClaimFromClaimsBlock();
        Thread.sleep(3000);
        // приступаем к созданию комментария
        ClaimFunc.scrollToLastComment();
        ClaimFunc.initiateCommentCreation();
        CommentFunc.fillInTheCommentField(comment);
        CommentFunc.saveComment();
        Thread.sleep(3000);
        ClaimFunc.scrollToLastComment();
        ClaimFunc.isCommentDisplayed(comment);
        // приступаем к редактированию комментария
        ClaimFunc.scrollToLastComment();
        ClaimFunc.initiateCommentEditing(comment);
        CommentFunc.fillInTheCommentField(commentForEditing);
        CommentFunc.saveComment();
        Thread.sleep(3000);
        ClaimFunc.scrollToLastComment();
        ClaimFunc.isCommentDisplayed(commentForEditing);
    }

}