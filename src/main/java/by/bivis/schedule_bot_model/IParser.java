package by.bivis.schedule_bot_model;

import by.bivis.schedule_bot_model.objects.parser_objects.News;
import by.bivis.schedule_bot_model.objects.parser_objects.Schedule;
import by.bivis.schedule_bot_model.objects.parser_objects.Source;

import java.util.List;

public interface IParser {
    List<News> getNews();
    List<Source<?>> getSourceList();
    Schedule getSchedule(Source<?> source);
}
