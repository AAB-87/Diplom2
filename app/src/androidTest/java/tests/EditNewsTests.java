package tests;

import static androidx.test.espresso.action.ViewActions.click;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.junit4.DisplayName;
import functions.AuthorizationFunc;
import functions.ControlPanelFunc;
import functions.NewsCreationAndEditingFunc;
import functions.NewsFunc;
import ru.iteco.fmhandroid.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;

import data.DataHelper;
import ru.iteco.fmhandroid.ui.AppActivity;
import screenElements.NewsScreen;

@RunWith(AllureAndroidJUnit4.class)
public class EditNewsTests {

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

    public void createNewsWithActiveStatus() {
        Allure.step("Создание новости с активным статусом");
        // общие параметры для создания/редактирования новости
        String emptyCategory = "no";
        String withCategoryChoice = "yes";
        String category = "no";
        String title = "no";
        String emptyDate = "no";
        String emptyTime = "no";
        String withDialPadOrTextInput = "dial";
        String saveOrCancelTime = "save";
        String emptyDescription = "no";
        // параметры новости
        String chosenCategory = "Зарплата";
        String description = "Description";
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.fillInTheNewsFields(emptyCategory, withCategoryChoice, chosenCategory, category, title, emptyDate, emptyTime, withDialPadOrTextInput, saveOrCancelTime, emptyDescription, description);
        NewsCreationAndEditingFunc.saveNews();
    }

    public void createNewsWithNotActiveStatus() throws InterruptedException {
        Allure.step("Создание заявки с НЕактивным статусом");
        String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        // параметры новости (должны совпадать с параметрами пердварительно созданной новости!!!!!)
        String chosenCategory = "Зарплата";
        String description = "Description";
        String finalStatus = "Not active";
        createNewsWithActiveStatus();
        ControlPanelFunc.goToNewsBlock();
        // проверяем,что новость, действительно, создана
        NewsFunc.checkNewsData(chosenCategory, description);
        NewsFunc.initiateNewsEditing(chosenCategory);
        // убеждаемся, что для изменения статуса выбрана именно ранее созданная новость
        NewsCreationAndEditingFunc.checkNewsInEditMode(chosenCategory, currentDate, description);
        // изменение статуса
        NewsCreationAndEditingFunc.changeNewsStatus();
        NewsCreationAndEditingFunc.saveNews();
        Thread.sleep(5000);
        ControlPanelFunc.goToClaimsBlock();
        ControlPanelFunc.goToNewsBlock();
        NewsScreen.editNewsButton.perform(click());
        // проверка, что новость имеет статус "На активна"
        //NewsPage.checkNewsStatus(chosenCategory,currentDate, finalStatus);
    }

    @Test // низкая стабильностьь теста
    @DisplayName("Редактирование новости при заполнении всех полей валидными данными (кирилические символы, текущая дата, текущее время в формате циферблата)")
    public void editNewsWithValidData() throws InterruptedException {
        // общие параметры для создания/редактирования новости
        String emptyCategory = "no";
        String withCategoryChoice = "yes";
        String category = "no";
        String title = "no";
        String emptyDate = "no";
        String emptyTime = "no";
        String withDialPadOrTextInput = "dial";
        String saveOrCancelTime = "save";
        String emptyDescription = "no";
        String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        // параметры старой новости
        String chosenCategory = "Зарплата";
        String description = "Description";
        // параметры для редактирования новости
        String newChosenCategory = "Массаж";
        String newDescription = "New description";
        // создаем новость
        createNewsWithActiveStatus();
        ControlPanelFunc.goToNewsBlock();
        // проверяем,что новость, действительно, создана
        NewsFunc.checkNewsData(chosenCategory, description);
        // переход к редактированию новости
        NewsFunc.initiateNewsEditing(chosenCategory);
        // убеждаемся, что для редактирования выбрана именно ранее созданная новость
        Thread.sleep(2000);
        NewsCreationAndEditingFunc.checkNewsInEditMode(chosenCategory, currentDate, description);
        // редактирование новости
        NewsCreationAndEditingFunc.fillInTheNewsFields(emptyCategory, withCategoryChoice, newChosenCategory, category, title, emptyDate, emptyTime, withDialPadOrTextInput, saveOrCancelTime, emptyDescription, newDescription);
        NewsCreationAndEditingFunc.saveNews();
        ControlPanelFunc.goToNewsBlock();
        // проверяем,что новость, действительно, отредактирована (данные обновились)
        NewsFunc.checkFirstNewsDataAfterEdit(newChosenCategory, newDescription, currentDate);
        // удаление новости
        NewsFunc.goToEditingModeForNews();
        NewsFunc.deleteNews(newChosenCategory);
    }

    @Test  // тест проходит (без удаления)
    @DisplayName("Изменение статуса с \"Активна\" на \"Не активна\" при редактировании новости")
    public void shouldChangeNewsStatusToNotActive() throws InterruptedException {
        // общие параметры для редактирования новости
        String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        // параметры новости (должны совпадать с параметрами пердварительно созданной новости!!!!!)
        String chosenCategory = "Зарплата";
        String description = "Description";
        String finalStatus = "Not active";
        // создаем новость
        createNewsWithActiveStatus();
        ControlPanelFunc.goToNewsBlock();
        // проверяем,что новость, действительно, создана
        NewsFunc.checkNewsData(chosenCategory, description);
        // переход к редактированию новости
        NewsFunc.initiateNewsEditing(chosenCategory);
        // убеждаемся, что для изменения статуса выбрана именно ранее созданная новость
        NewsCreationAndEditingFunc.checkNewsInEditMode(chosenCategory, currentDate, description);
        // изменение статуса
        NewsCreationAndEditingFunc.changeNewsStatus();
        NewsCreationAndEditingFunc.saveNews();
        Thread.sleep(5000);
        ControlPanelFunc.goToClaimsBlock();
        ControlPanelFunc.goToNewsBlock();
        NewsScreen.editNewsButton.perform(click());
        // проверка, что новость имеет статус "На активна"
        NewsFunc.checkNewsStatus(chosenCategory, currentDate, finalStatus);
        // проверка, что новость исчезла из панели новостей (допущение, что сохраненная ноовсть всегда показывается первой)
        ControlPanelFunc.goToClaimsBlock();
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.checkThatNewsDoesNotExist(chosenCategory, description);
        // удаление новости
        NewsFunc.goToEditingModeForNews();
        NewsFunc.deleteNews(chosenCategory);
    }

    @Test // падает по техническим причинам!
    @DisplayName("Изменение статуса с \"Не активна\" на \"Активна\" при редактировании новости")
    public void shouldChangeNewsStatusToActive() throws InterruptedException {
        String chosenCategory = "Зарплата";
        String description = "Description";
        String finalStatus = "Active";
        String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        createNewsWithNotActiveStatus();
        onView(DataHelper.withIndex(withId(R.id.view_news_item_image_view), 0)).perform(click());
        onView(DataHelper.withIndex(withContentDescription("News editing button"), 0)).perform(click());
        // убеждаемся, что для изменения статуса выбрана именно ранее созданная новость
        NewsCreationAndEditingFunc.checkNewsInEditMode(chosenCategory, currentDate, description);
        // изменение статуса
        NewsCreationAndEditingFunc.changeNewsStatus();
        NewsCreationAndEditingFunc.saveNews();
        // проверка, что новость имеет статус активна
        NewsFunc.checkNewsStatus(chosenCategory, currentDate, finalStatus);
        // проверка, что новость снова видна в блоке "Новости"
        ControlPanelFunc.goToNewsBlock();
        //checkFirstNewsDataAfterEdit(chosenCategory,description,currentDate);
        NewsFunc.checkNewsData(chosenCategory, description);
    }

}