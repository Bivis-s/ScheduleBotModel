package by.bivis.schedule_bot_model;

import by.bivis.schedule_bot_model.objects.db_objects.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public abstract class ScheduleBotModel<USER, NEWS, SOURCE, SCHEDULE> {
    private NewsDao<NEWS> newsDao;
    private SourceDao<SOURCE> sourceDao;
    private ScheduleDao<SCHEDULE> scheduleDao;
    private UserDao<USER> userDao;
    private Parser<NEWS, SOURCE, SCHEDULE> parser;
    private ScheduleBotView<USER, NEWS, SCHEDULE, SOURCE> view;

    protected abstract USER setStateToUser(USER user, UserState state);

    protected abstract USER setSelectedSourceCategoryToUser(USER user, String sourceCategory);

    protected abstract USER setSelectedSourceSubcategoryToUser(USER user, String sourceSubcategory);

    protected void updateUserState(USER user, UserState state) {
        getUserDao().update(setStateToUser(user, state));
    }

    protected void updateUserSelectedSourceCategory(USER user, String category) {
        getUserDao().update(setSelectedSourceCategoryToUser(user, category));
    }

    protected void updateUserSelectedSourceSubcategory(USER user, String subcategory) {
        getUserDao().update(setSelectedSourceSubcategoryToUser(user, subcategory));
    }

    // TODO вынести в отдельный класс
    private void updateSources(List<SOURCE> sources) {
        getSourceDao().update(sources.get(2));
    }

    public void updateSources() {
        List<SOURCE> sources = getParser().getSources();
        for (SOURCE source : sources) {
            getSourceDao().update(source);
        }
    }

    public void updateNews() {
        List<NEWS> newsList = getParser().getNews();
        for (NEWS news : newsList) {
            getNewsDao().update(news);
        }
    }

    private void updateSchedule(SCHEDULE schedule) {
        getScheduleDao().update(schedule);
    }

    public void updateSchedules() {
        for (SOURCE source : getSourceDao().getSignedSources()) {
            updateSchedule(getParser().getSchedule(source));
        }
    }

    public void sendNewsToView(USER user) {
        getView().sendNews(user, getParser().getNews());
        updateUserState(user, UserState.NEWS);
    }

    public void sendSourceCategoriesToView(USER user) {
        getView().sendSourceCategories(user, getParser().getSourceCategorySet());
        updateUserState(user, UserState.PICK_SOURCE_CATEGORY);
    }

    public void sendSourcesSubcategoryByCategoryToView(USER user, String category) {
        getView().sendSourcesSubcategoryByCategory(user, getParser().getSourceSubcategoryList(category));
        updateUserState(user, UserState.SOURCES_SUBCATEGORIES);
    }

    public void sendSelectedSource(USER user, String category, String subcategory) {
        getView().sendSources(getSourceDao().getSourcesByCategoryAndSubcategory(category, subcategory));
    }

    public void sendSubscriptionsToView(USER user, long userId) {
        getView().sendSignedSources(user, getSourceDao().getSignedSources(userId));
        updateUserState(user, UserState.PICK_SIGNED_SOURCE);
    }

    public void sendScheduleToView(USER user, SOURCE source) {
        getView().sendSchedule(user, getScheduleDao().get(source));
        updateUserState(user, UserState.SCHEDULE);
    }

    public void sendHelloMessageToView(USER user) {
        getView().sendHelloMessage(user, "Щеее не вмерла Украина!"); //TODO вынести весь статичный текст в отдельный класс/интерфейс
        updateUserState(user, UserState.START);
    }

    public abstract void handleCommandByUserState(USER user, String command);
}
