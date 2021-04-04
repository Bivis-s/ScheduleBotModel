package by.bivis.schedule_bot_model;

import by.bivis.schedule_bot_model.objects.parser_objects.Schedule;
import by.bivis.schedule_bot_model.objects.parser_objects.Source;
import by.bivis.schedule_bot_model.objects.parser_objects.User;
import by.bivis.schedule_bot_model.objects.parser_objects.UserState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ScheduleBotModel<T extends User> {
    private final Dao dao;
    private final Parser parser;
    private final ScheduleBotView<T> view;
    private UserState userState; //TODO Временное состояние юзера, заменить связью с бд

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

    public void sendNewsToView(T user) {
        getView().sendNews(user, getParser().getNews());
        userState = UserState.NEWS; //TODO Заменить связью с бд
    }

    public void sendSourceCategoriesToView(T user) {
        getView().sendSourceCategories(user, getParser().getSourceCategorySet());
        userState = UserState.SOURCE_CATEGORIES; //TODO Заменить связью с бд
    }

    public void sendSourcesSubcategoryByCategoryToView(T user, String category) {
        getView().sendSourcesSubcategoryByCategory(user, getParser().getSourceSubcategoryList(category));
        userState = UserState.SOURCES; //TODO Заменить связью с бд
    }

    public void sendSchedulesToView(T user) {
        getView().sendSchedules(user, getDao().getSchedules(getDao().getUserSubscriptions(user)));
    }

    public void sendHelloMessageToView(T user) {
        getView().sendHelloMessage(user, "Щеее не вмерла Украина!"); //TODO вынести весь статичный текст в отдельный класс/интерфейс
        userState = UserState.START; //TODO Заменить связью с бд
    }

    public void handle(T user, String message) {
        switch (message) {
            case "/start": //TODO Вынести в отдельный интерфейс
                sendHelloMessageToView(user);
                break;
            case "/news":
                sendNewsToView(user);
                break;
            case "/subscribe":
                sendSourceCategoriesToView(user);
                break;
            default:
                switch (userState) {
                    case SOURCE_CATEGORIES:
                        sendSourcesSubcategoryByCategoryToView(user, message);
                        break;
                    case SOURCES_SUBCATEGORIES:
                        //TODO ??
                        break;
                    case SOURCES:
                        //TODO Implement
                        break;
                    default:
                        break;
                }
        }
    }
}
