package by.bivis.schedule_bot_model;

import by.bivis.schedule_bot_model.objects.parser_objects.Schedule;
import by.bivis.schedule_bot_model.objects.parser_objects.Source;
import by.bivis.schedule_bot_model.objects.parser_objects.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ScheduleBotModel {
    private Dao dao;
    private IParser parser;
    private ScheduleBotView view;

    public void updateSources() {
        getDao().updateSources(getParser().getSourceList());
    }

    public void updateNews() {
        getDao().updateNews(getParser().getNews());
    }

    private void updateSchedule(Schedule schedule) {
        getDao().updateSchedules(schedule);
    }

    public void updateSignedSourceSchedules() {
        for (Source<?> source : getDao().getSignedSources()) {
            updateSchedule(getParser().getSchedule(source));
        }
    }

    public void sendNewsToView(User user) {
        getView().sendNews(user);
    }

    public void sendSourcesToView(User user) {
        getView().sendSources(user);
    }

    public void sendSchedulesToView(User user) {
        getView().sendSchedules(user, getDao().getSchedules(getDao().getUserSubscriptions(user)));
    }

    public void sendHelloMessageToView(User user) {
        getView().sendHelloMessage(user);
    }
}
