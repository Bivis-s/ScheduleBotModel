package by.bivis.schedule_bot_model;

import by.bivis.schedule_bot_model.objects.parser_objects.News;
import by.bivis.schedule_bot_model.objects.parser_objects.Schedule;
import by.bivis.schedule_bot_model.objects.parser_objects.Source;
import by.bivis.schedule_bot_model.objects.parser_objects.User;

import java.util.List;

public interface Dao {
    void updateNews(List<News> news);
    List<News> getNews();
    void updateSources(List<Source<?>> sources);
    List<Source<?>> getSources();
    List<Source<?>> getSignedSources();
    List<Source<?>> getUserSubscriptions(User user);
    void updateSchedules(Schedule schedule);
    List<Schedule> getSchedules(List<Source<?>> sources);
}
