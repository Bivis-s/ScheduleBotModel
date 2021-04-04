package by.bivis.schedule_bot_model;

import by.bivis.schedule_bot_model.objects.parser_objects.Schedule;
import by.bivis.schedule_bot_model.objects.parser_objects.User;

import java.util.List;

public interface ScheduleBotView {
    void sendNews(User user);
    void sendSources(User user);
    void sendSchedules(User user, List<Schedule> schedule);

    void sendHelloMessage(User user);
}
