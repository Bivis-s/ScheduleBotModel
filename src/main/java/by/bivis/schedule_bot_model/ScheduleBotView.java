package by.bivis.schedule_bot_model;

import by.bivis.schedule_bot_model.objects.parser_objects.News;
import by.bivis.schedule_bot_model.objects.parser_objects.Schedule;
import by.bivis.schedule_bot_model.objects.parser_objects.User;

import java.util.List;
import java.util.Set;

public interface ScheduleBotView<T extends User> {
    void sendNews(T user, List<News> news);

    void sendSourceCategories(T user, Set<String> sources);

    void sendSourcesSubcategoryByCategory(T user, Set<String> sourceSubcategories);

    void sendSchedules(T user, List<Schedule> schedule);

    void sendHelloMessage(T user, String message);

}
