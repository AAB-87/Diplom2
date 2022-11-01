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
import functions.ControlPanelFunc;
import functions.NewsCreationAndEditingFunc;
import functions.NewsSteps;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AllureAndroidJUnit4.class)
public class NewsTests {

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

    @Test // в одиночку проходит
    @DisplayName("Наличие новостей в блоке \"Новости\" (минимум 3)")
    public void shouldBeThreeNewsInNewsBlock() {
        ControlPanelFunc.goToNewsBlock();
        NewsSteps.checkThatThereAreThreeNewsItemsInTheNewsBlock();
    }

    @Test // в одиночку проходит
    @DisplayName("Полнота информации новостей (в развернутом состоянии) в блоке \"Новости\"")
    public void shouldBeFullContentOfFirstExpandedNewsInNewsBlock() {
        ControlPanelFunc.goToNewsBlock();
        NewsSteps.expandFirstNewsInNewsBlock();
        NewsSteps.checkBasicContentOfFirstExpandedNewsInNewsBlock();
    }

    @Test // проблемы со стабильностью
    @DisplayName("Удаление новости")
    public void shouldDeleteNews() throws InterruptedException {
        String emptyCategory = "no";
        String withCategoryChoice = "yes";
        String chosenCategory = "Зарплата";
        String category = "no";
        String title = "no";
        String emptyDate = "no";
        String emptyTime = "no";
        String withDialPadOrTextInput = "dial";
        String saveOrCancelTime = "save";
        String emptyDescription = "no";
        String description = "New description";
        ControlPanelFunc.goToNewsBlock();
        NewsSteps.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.fillInTheNewsFields(emptyCategory, withCategoryChoice, chosenCategory, category, title, emptyDate, emptyTime, withDialPadOrTextInput, saveOrCancelTime, emptyDescription, description);
        NewsCreationAndEditingFunc.saveNews();
        ControlPanelFunc.goToNewsBlock();
        NewsSteps.checkNewsData(chosenCategory, description);
        NewsSteps.goToEditingModeForNews();
        NewsSteps.deleteNews(chosenCategory);
        ControlPanelFunc.goToNewsBlock();
        Thread.sleep(3000);
        NewsSteps.checkThatNewsDoesNotExist(chosenCategory, description);
    }
}