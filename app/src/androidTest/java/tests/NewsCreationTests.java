package tests;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import functions.AuthorizationFunc;
import functions.ControlPanelFunc;
import functions.NewsCreationAndEditingFunc;
import functions.NewsFunc;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AllureAndroidJUnit4.class)
public class NewsCreationTests {

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

    @Test // БАГ!!!!!!!
    @DisplayName("Создание новости при заполнении всех полей валидными данными (кириллические символы,текущая дата, текущее время в формате циферблата)")
    public void shouldCreateNewsWithTextInputInCategoryAndValidData() throws InterruptedException {
        String emptyCategory = "no";
        String withCategoryChoice = "no";
        String chosenCategory = "no";
        String category = "Very important news";
        String title = "Super News";
        String emptyDate = "no";
        String emptyTime = "no";
        String withDialPadOrTextInput = "dial";
        String saveOrCancelTime = "save";
        String emptyDescription = "no";
        String description = "New description";
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.fillInTheNewsFields(emptyCategory, withCategoryChoice, chosenCategory, category, title, emptyDate, emptyTime, withDialPadOrTextInput, saveOrCancelTime, emptyDescription, description);
        NewsCreationAndEditingFunc.saveNews();
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.checkNewsData(chosenCategory, description);
    }

    @Test // тест проходит на эмуляторе (даже с удалением), нестабильный при запуске всех тестов
    @DisplayName("Выбор категории \"Зарплата\" из списка с автозаполнением заголовка при создании новости")
    public void shouldCreateNewsWithCategoryChoiceAndValidData() {
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
        NewsFunc.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.fillInTheNewsFields(emptyCategory, withCategoryChoice, chosenCategory, category, title, emptyDate, emptyTime, withDialPadOrTextInput, saveOrCancelTime, emptyDescription, description);
        NewsCreationAndEditingFunc.saveNews();
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.checkNewsData(chosenCategory, description);
        // удаление новости
        NewsFunc.goToEditingModeForNews();
        NewsFunc.deleteNews(chosenCategory);
    }

    @Test  // БАГ!!! Новость не появляется в блоке новостей
    @DisplayName("Ручной ввод часов и минут при при создании новости")
    public void shouldCreateNewsWithManualTimeInput() {
        String emptyCategory = "no";
        String withCategoryChoice = "yes";
        String chosenCategory = "Зарплата";
        String category = "no";
        String title = "no";
        String emptyDate = "no";
        String emptyTime = "no";
        String withDialPadOrTextInput = "textInput";
        String saveOrCancelTime = "save";
        String emptyDescription = "no";
        String description = "New description";
        // будет введено время: 02:23 (заложено в методе, также в методе есть проверка, что введено именно это время)
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.fillInTheNewsFields(emptyCategory, withCategoryChoice, chosenCategory, category, title, emptyDate, emptyTime, withDialPadOrTextInput, saveOrCancelTime, emptyDescription, description);
        NewsCreationAndEditingFunc.saveNews();
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.checkNewsData(chosenCategory, description);
        // удаление новости
        NewsFunc.goToEditingModeForNews();
        NewsFunc.deleteNews(chosenCategory);
    }

    @Test  // не самый стабильный тест
    @DisplayName("Отмена создания новости")
    public void shouldCancelNewsCreation() throws InterruptedException {
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
        NewsFunc.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.fillInTheNewsFields(emptyCategory, withCategoryChoice, chosenCategory, category, title, emptyDate, emptyTime, withDialPadOrTextInput, saveOrCancelTime, emptyDescription, description);
        NewsCreationAndEditingFunc.сancelSavingNews();
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.checkThatNewsDoesNotExist(chosenCategory, description);
    }

    @Test
    @DisplayName("Не выбрана категория при создании новости")
    public void shouldCreateNewsWithEmptyCategory() {
        String emptyCategory = "yes";
        String withCategoryChoice = "no";
        String chosenCategory = "Зарплата";
        String category = "no";
        String title = "Super News";
        String emptyDate = "no";
        String emptyTime = "no";
        String withDialPadOrTextInput = "dial";
        String saveOrCancelTime = "save";
        String emptyDescription = "no";
        String description = "New description";
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.fillInTheNewsFields(emptyCategory, withCategoryChoice, chosenCategory, category, title, emptyDate, emptyTime, withDialPadOrTextInput, saveOrCancelTime, emptyDescription, description);
        NewsCreationAndEditingFunc.saveNews();
        NewsCreationAndEditingFunc.checkMessageThatFieldShouldBeFilled(activityTestRule);
    }

    @Test
    @DisplayName("Пустой ввод в поле \"Описание\" при создании новости")
    public void shouldCreateNewsWithEmptyDescription() {
        String emptyCategory = "no";
        String withCategoryChoice = "yes";
        String chosenCategory = "Зарплата";
        String category = "no";
        String title = "Super News";
        String emptyDate = "no";
        String emptyTime = "no";
        String withDialPadOrTextInput = "dial";
        String saveOrCancelTime = "save";
        String emptyDescription = "yes";
        String description = "no";
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.fillInTheNewsFields(emptyCategory, withCategoryChoice, chosenCategory, category, title, emptyDate, emptyTime, withDialPadOrTextInput, saveOrCancelTime, emptyDescription, description);
        NewsCreationAndEditingFunc.saveNews();
        NewsCreationAndEditingFunc.checkMessageThatFieldShouldBeFilled(activityTestRule);
    }

    @Test
    @DisplayName("Cоздание новости без выбора даты")
    public void shouldCreateNewsWithEmptyDate() {
        String emptyCategory = "no";
        String withCategoryChoice = "yes";
        String chosenCategory = "Зарплата";
        String category = "no";
        String title = "Super News";
        String emptyDate = "yes";
        String emptyTime = "no";
        String withDialPadOrTextInput = "dial";
        String saveOrCancelTime = "save";
        String emptyDescription = "no";
        String description = "New description";
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.fillInTheNewsFields(emptyCategory, withCategoryChoice, chosenCategory, category, title, emptyDate, emptyTime, withDialPadOrTextInput, saveOrCancelTime, emptyDescription, description);
        NewsCreationAndEditingFunc.saveNews();
        NewsCreationAndEditingFunc.checkMessageThatFieldShouldBeFilled(activityTestRule);
    }

    @Test
    @DisplayName("Ввод > 24 часов в поле часы при создании новости")
    public void shouldInputMoreThan24HoursWhenNewsIsBeingCreated() {
        String invalidHours = "76";
        String validMinutes = "23";
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.timeInput(invalidHours, validMinutes);
        NewsCreationAndEditingFunc.checkMessageOfTimeInputError();
    }

    @Test
    @DisplayName("Ввод >60 в поле минуты при создании новости")
    public void shouldInputMoreThan60MinutesWhenNewsIsBeingCreated() {
        String validHours = "22";
        String invalidMinutes = "68";
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.timeInput(validHours, invalidMinutes);
        NewsCreationAndEditingFunc.checkMessageOfTimeInputError();
    }

    @Test
    @DisplayName("Отмена выбора времени в разделе циферблат при создании новости")
    public void shouldCancelSavingTimeWhenNewsAreBeingCreated() {
        String emptyCategory = "no";
        String withCategoryChoice = "yes";
        String chosenCategory = "Зарплата";
        String category = "no";
        String title = "no";
        String emptyDate = "no";
        String emptyTime = "no";
        String withDialPadOrTextInput = "dial";
        String saveOrCancelTime = "cancel";
        String emptyDescription = "no";
        String description = "New description";
        ControlPanelFunc.goToNewsBlock();
        NewsFunc.initiateTheCreationOfNews();
        NewsCreationAndEditingFunc.fillInTheNewsFields(emptyCategory, withCategoryChoice, chosenCategory, category, title, emptyDate, emptyTime, withDialPadOrTextInput, saveOrCancelTime, emptyDescription, description);
        NewsCreationAndEditingFunc.saveNews();
        NewsCreationAndEditingFunc.checkMessageThatFieldShouldBeFilled(activityTestRule);
    }

}