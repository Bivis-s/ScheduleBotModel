package by.bivis.schedule_bot_model;

import java.util.List;
import java.util.Set;

public interface Parser<NEWS, SOURCE, SCHEDULE> { // TODO Сделать прокси между парсером и моделью, связать с бд
    List<NEWS> getNews();

    SCHEDULE getSchedule(SOURCE source);

    List<SOURCE> getSources();

    //TODO ----------------------------------------------
    // Всё что ниже в отдельный класс, связанный с моделью
    // т.к. брать данные надо не с сайта, соотв. связи с парсером нет

    Set<String> getSourceCategorySet();

    Set<String> getSourceSubcategoryList(String category);

    Set<SOURCE> getSources(String category, String subcategory);
}
