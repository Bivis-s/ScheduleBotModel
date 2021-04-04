package by.bivis.schedule_bot_model;

import by.bivis.schedule_bot_model.objects.parser_objects.News;
import by.bivis.schedule_bot_model.objects.parser_objects.Schedule;
import by.bivis.schedule_bot_model.objects.parser_objects.Source;

import java.util.List;
import java.util.Set;

public interface Parser { // TODO Сделать прокси между парсером и моделью, связать с бд
    List<News> getNews();
    Schedule getSchedule(Source<?> source);
    List<Source<?>> getSourceList();
    List<Source<?>> getSourceList(String subcategory);
    Set<String> getSourceCategorySet();
    Set<String> getSourceSubcategoryList(String category);
}
