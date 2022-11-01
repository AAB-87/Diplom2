package screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;

import data.DataHelper;
import ru.iteco.fmhandroid.R;


public class NewsScreen {

    // Заголовок блока "Новости"
    public static ViewInteraction titleOfNewsBlock = onView(withText("News"));

    // Раскрыть новость в блоке новостей
    public static ViewInteraction openFirstNewsInNewsBlock = onView(DataHelper.withIndex(withId(R.id.view_news_item_image_view), 0));
    public static ViewInteraction openSecondNewsInNewsBlock = onView(DataHelper.withIndex(withId(R.id.view_news_item_image_view), 1));

    // Элементы раскрытой новости
    public static ViewInteraction firstCardNews = onView(DataHelper.withIndex(withId(R.id.news_item_material_card_view), 0));
    public static ViewInteraction firstCardNews2 = onView(
            allOf(withId(R.id.news_list_recycler_view),
                    DataHelper.childAtPosition(
                            withId(R.id.all_news_cards_block_constraint_layout),
                            0)));
    public static ViewInteraction secondCardNews = onView(DataHelper.withIndex(withId(R.id.news_item_material_card_view), 1));
    public static ViewInteraction thirdCardNews = onView(DataHelper.withIndex(withId(R.id.news_item_material_card_view), 2));
    public static ViewInteraction firstNewsItemTitle = onView(DataHelper.withIndex(withId(R.id.news_item_title_text_view), 0));
    public static ViewInteraction firstNewsItemTitle2 = onView(allOf(withId(R.id.news_item_title_text_view), withParent(withParent(DataHelper.withIndex(withId(R.id.news_item_material_card_view), 0)))));
    public static ViewInteraction secondNewsItemTitle = onView(DataHelper.withIndex(withId(R.id.news_item_title_text_view), 1));
    public static ViewInteraction thirdNewsItemTitle = onView(DataHelper.withIndex(withId(R.id.news_item_title_text_view), 2));
    public static ViewInteraction firstNewsItemDescription = onView(DataHelper.withIndex(withId(R.id.news_item_description_text_view), 0));
    public static ViewInteraction firstNewsItemDescription2 = onView(allOf(withId(R.id.news_item_description_text_view), withParent(withParent(DataHelper.withIndex(withId(R.id.news_item_material_card_view), 0)))));
    public static ViewInteraction firstNewsItemDate = onView(DataHelper.withIndex(withId(R.id.news_item_date_text_view), 0));

    // Кнопка редактирования новостей
    public static ViewInteraction editNewsButton = onView(withId(R.id.edit_news_material_button));

    // Кнопка редактирования конкретной новости
    public static ViewInteraction editFirstNewsItemButton = onView(DataHelper.withIndex(withId(R.id.edit_news_item_image_view), 0));

    // Создание новости
    public static ViewInteraction addNewsButton = onView(withId(R.id.add_news_image_view));

    // Удаление новости
    public static ViewInteraction deleteFirstNewsButton = onView(DataHelper.withIndex(withId(R.id.delete_news_item_image_view), 0));

    // Кнопка "ОК"
    public static ViewInteraction okButton = onView(withText("OK"));

    // Сортировка новостей
    public static ViewInteraction sortNewsButton = onView(withId(R.id.sort_news_material_button));
}