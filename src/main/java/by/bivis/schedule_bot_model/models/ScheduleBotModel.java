package by.bivis.schedule_bot_model.models;

import by.bivis.schedule_bot_model.Parser;
import by.bivis.schedule_bot_model.UserState;
import by.bivis.schedule_bot_model.objects.db_objects.NewsDao;
import by.bivis.schedule_bot_model.objects.db_objects.ScheduleDao;
import by.bivis.schedule_bot_model.objects.db_objects.SourceDao;
import by.bivis.schedule_bot_model.objects.db_objects.UserDao;
import by.bivis.schedule_bot_model.views.ScheduleBotView;
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
    private ScheduleDao<SCHEDULE, SOURCE> scheduleDao;
    private UserDao<USER, SOURCE> userDao;
    private Parser<NEWS, SOURCE, SCHEDULE> parser;
    private ScheduleBotView<USER, NEWS, SCHEDULE, SOURCE> view;

    //TODO рефактор этих методов

    protected abstract USER setStateToUser(USER user, UserState state);

    protected abstract USER setSelectedSourceCategoryToUser(USER user, String sourceCategory);

    protected abstract USER setSelectedSourceSubcategoryToUser(USER user, String sourceSubcategory);

    protected abstract long getUserId(USER user);

    protected abstract long getSourceId(SOURCE source);

    protected abstract USER setSelectedSourceCategoryAndSubcategoryToNull(USER user);

    public abstract void handleCommandByUserState(USER user, String command);

    protected void cleanSelectedSourceCategoryAndSubcategory(USER user) {
        getUserDao().update(setSelectedSourceCategoryAndSubcategoryToNull(user));
    }

    protected void updateUserState(USER user, UserState state) {
        getUserDao().update(setStateToUser(user, state));
    }

    protected void updateUserSelectedSourceCategory(USER user, String category) {
        getUserDao().update(setSelectedSourceCategoryToUser(user, category));
    }

    protected void updateUserSelectedSourceSubcategory(USER user, String subcategory) {
        getUserDao().update(setSelectedSourceSubcategoryToUser(user, subcategory));
    }

    protected void addSubscriptionToUser(USER user, String sourceName) {
        SOURCE source = getSourceDao().getSelectedSource(getUserId(user),
                getUserDao().getSelectedSourceCategory(getUserId(user)),
                getUserDao().getSelectedSourceSubcategory(getUserId(user)),
                sourceName);
        if (getUserDao().isThereSuchSubscription(getUserId(user), source)) {
            sendThereIsAlreadySuchSubscriptionMessageToView(user);
        } else {
            getUserDao().addSubscriptionToUser(getUserId(user), source);
            sendSubscriptionSuccessMessageToView(user);
            sendTodayAndTomorrowScheduleToView(user, source);
        }
        cleanSelectedSourceCategoryAndSubcategory(user);
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
            updateSchedule(getParser().getTodayAndTomorrowSchedule(source));
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

    public void sendSelectedSourceToView(USER user) {
        String category = getUserDao().getSelectedSourceCategory(getUserId(user));
        String subcategory = getUserDao().getSelectedSourceSubcategory(getUserId(user));
        getView().sendSources(user, getSourceDao().getSourcesByCategoryAndSubcategory(category, subcategory));
        updateUserState(user, UserState.SOURCES);
    }

    public void sendSubscriptionsToView(USER user) {
        getView().sendSources(user, getSourceDao().getSignedSources(getUserId(user)));
        updateUserState(user, UserState.PICK_SIGNED_SOURCE);
    }

    public void sendSubscriptionsToSeeExtendedScheduleToView(USER user) {
        getView().sendSources(user, getSourceDao().getSignedSources(getUserId(user)));
        updateUserState(user, UserState.PICK_SIGNED_SOURCE_EXTENDED);
    }

    public void sendTodayAndTomorrowScheduleToView(USER user, SOURCE source) {
        getView().sendTodayAndTomorrowSchedule(user, getParser().getTodayAndTomorrowSchedule(source));
        updateUserState(user, UserState.SCHEDULE);
    }

    public void sendExtendedScheduleToView(USER user, SOURCE source) {
        getView().sendExtendedSchedule(user, getParser().getExtendedSchedule(source));
        updateUserState(user, UserState.SCHEDULE);
    }

    public void sendHelloMessageToView(USER user) {
        getView().sendInfoMessage(user);
        updateUserState(user, UserState.START);
    }

    public void sendParsingInProcessMessageToView(USER user) {
        getView().sendParsingInProcessMessage(user);
    }

    public void sendThereIsAlreadySuchSubscriptionMessageToView(USER user) {
        getView().sendThereIsAlreadySuchSubscriptionMessage(user);
    }

    public void sendSubscriptionSuccessMessageToView(USER user) {
        getView().sendSubscriptionSuccessMessage(user);
    }
}
