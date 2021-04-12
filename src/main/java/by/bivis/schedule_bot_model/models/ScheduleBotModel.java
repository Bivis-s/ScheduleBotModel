package by.bivis.schedule_bot_model.models;

import by.bivis.schedule_bot_model.enums.UserState;
import by.bivis.schedule_bot_model.errors.SourceWasntFoundError;
import by.bivis.schedule_bot_model.objects.db_objects.NewsDao;
import by.bivis.schedule_bot_model.objects.db_objects.ScheduleDao;
import by.bivis.schedule_bot_model.objects.db_objects.SourceDao;
import by.bivis.schedule_bot_model.objects.db_objects.UserDao;
import by.bivis.schedule_bot_model.parser.Parser;
import by.bivis.schedule_bot_model.views.ScheduleBotView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class ScheduleBotModel<USER, NEWS, SOURCE, SCHEDULE, NOTE> {
    private NewsDao<NEWS> newsDao;
    private SourceDao<SOURCE> sourceDao;
    private ScheduleDao<SCHEDULE, SOURCE> scheduleDao;
    private UserDao<USER, SOURCE, NOTE> userDao;
    private Parser<NEWS, SOURCE, SCHEDULE> parser;
    private ScheduleBotView<USER, NEWS, SCHEDULE, SOURCE, NOTE> view;

    protected abstract USER setStateToUser(USER user, UserState state);

    protected abstract USER setSelectedSourceCategoryToUser(USER user, String sourceCategory);

    protected abstract USER setSelectedSourceSubcategoryToUser(USER user, String sourceSubcategory);

    public abstract long getUserId(USER user);

    protected abstract long getSourceId(SOURCE source);

    protected abstract USER setSelectedSourceCategoryAndSubcategoryToNull(USER user);

    protected abstract USER setNotifyTimeToUser(USER user, String notifyTime);

    public void cleanSelectedSourceCategoryAndSubcategory(USER user) {
        getUserDao().update(setSelectedSourceCategoryAndSubcategoryToNull(user));
    }

    public void updateUserState(USER user, UserState state) {
        getUserDao().update(setStateToUser(user, state));
    }

    public void updateUserSelectedSourceCategory(USER user, String category) {
        getUserDao().update(setSelectedSourceCategoryToUser(user, category));
    }

    public void updateUserSelectedSourceSubcategory(USER user, String subcategory) {
        getUserDao().update(setSelectedSourceSubcategoryToUser(user, subcategory));
    }

    private SOURCE getSelectedSourceByName(USER user, String sourceName) {
        return getSourceDao().getSelectedSource(getUserId(user),
                getUserDao().getSelectedSourceCategory(getUserId(user)),
                getUserDao().getSelectedSourceSubcategory(getUserId(user)),
                sourceName);
    }

    private SOURCE getSignedSourceByName(USER user, String sourceName) {
        return getSourceDao().getSourceFromSubscriptionsByName(getUserId(user), sourceName);
    }

    public void addSubscriptionToUser(USER user, String sourceName) {
        try {
            SOURCE source = getSelectedSourceByName(user, sourceName);
            if (getUserDao().isThereSuchSubscription(getUserId(user), source)) {
                sendThereIsAlreadySuchSubscriptionMessageToView(user);
            } else {
                getUserDao().addSubscriptionToUser(getUserId(user), source);
                sendSubscriptionSuccessMessageToView(user);
                sendTodayAndTomorrowScheduleToView(user, source);
            }
            cleanSelectedSourceCategoryAndSubcategory(user);
        } catch (SourceWasntFoundError error) {
            sendThereIsNoSuchSourceMessageToView(user);
        }
    }

    public void removeSubscriptionFromUser(USER user, String sourceName) {
        try {
            SOURCE source = getSignedSourceByName(user, sourceName);
            if (!(getUserDao().isThereSuchSubscription(getUserId(user), source))) {
                sendThereIsNoSuchSubscriptionMessageToView(user);
            } else {
                getUserDao().removeSubscriptionFromUser(getUserId(user), source);
                sendSubscriptionRemoveWasSuccessfulMessageToView(user);
            }
            cleanSelectedSourceCategoryAndSubcategory(user);
        } catch (SourceWasntFoundError error) {
            sendThereIsNoSuchSourceMessageToView(user);
        }
    }

    public void setNotifyToUser(USER user, String notifyTime, String sourceName) {
        try {
            SOURCE source = getSignedSourceByName(user, sourceName);
            if (getUserDao().isThereNotification(getUserId(user), notifyTime, source)) {
                sendThereIsAlreadySuchNotificationMessageToView(user);
            } else {
                getUserDao().addNotifyToUser(getUserId(user), notifyTime, source);
                sendNotifySuccessfullySetMessageToView(user);
            }
            cleanSelectedSourceCategoryAndSubcategory(user);
        } catch (SourceWasntFoundError error) {
            sendThereIsNoSuchSourceMessageToView(user);
        }
    }

    public void sendNewsToView(USER user) {
        getView().sendNews(user, getParser().getNews());
        updateUserState(user, UserState.NEWS);
    }

    public void sendSourceCategoriesToView(USER user) {
        getView().sendSourceCategories(user, getSourceDao().getSourceCategorySet());
        updateUserState(user, UserState.PICK_SOURCE_CATEGORY);
    }

    public void sendSourceCategoriesToSeeWithoutSubscriptionToView(USER user) {
        getView().sendSourceCategories(user, getSourceDao().getSourceCategorySet());
        updateUserState(user, UserState.PICK_SOURCE_CATEGORY_TO_SEE_WITHOUT_SUBSCRIPTION);
    }

    public void sendSourcesSubcategoryByCategoryToView(USER user, String category) {
        getView().sendSourcesSubcategoryByCategory(user, getSourceDao().getSourceSubcategoryList(category));
        updateUserState(user, UserState.SOURCES_SUBCATEGORIES);
    }

    public void sendSourcesSubcategoryByCategoryToSeeWithoutSubscriptionToView(USER user, String category) {
        getView().sendSourcesSubcategoryByCategory(user, getSourceDao().getSourceSubcategoryList(category));
        updateUserState(user, UserState.SOURCES_SUBCATEGORIES_TO_SEE_WITHOUT_SUBSCRIPTION);
    }

    private void sendSelectedSourceToViewBase(USER user) {
        String category = getUserDao().getSelectedSourceCategory(getUserId(user));
        String subcategory = getUserDao().getSelectedSourceSubcategory(getUserId(user));
        getView().sendSources(user, getSourceDao().getSourcesByCategoryAndSubcategory(category, subcategory));
    }

    public void sendSelectedSourceToView(USER user) {
        sendSelectedSourceToViewBase(user);
        updateUserState(user, UserState.SOURCES);
    }

    public void sendSelectedSourceToSeeWithoutSubscriptionToView(USER user) {
        sendSelectedSourceToViewBase(user);
        updateUserState(user, UserState.SEE_SCHEDULE_WITHOUT_SUBSCRIPTION);
    }

    public void sendSubscriptionsToView(USER user) {
        if (getUserDao().isThereSubscriptions(getUserId(user))) {
            getView().sendSources(user, getSourceDao().getSignedSources(getUserId(user)));
            updateUserState(user, UserState.PICK_SIGNED_SOURCE);
        } else {
            sendUserHasNoSubscriptionsMessageToView(user);
        }
    }

    public void sendSubscriptionsToSeeExtendedScheduleToView(USER user) {
        if (getUserDao().isThereSubscriptions(getUserId(user))) {
        getView().sendSources(user, getSourceDao().getSignedSources(getUserId(user)));
        updateUserState(user, UserState.PICK_SIGNED_SOURCE_EXTENDED);
        } else {
            sendUserHasNoSubscriptionsMessageToView(user);
        }
    }

    public void sendSubscriptionsToRemoveToView(USER user) {
        if (getUserDao().isThereSubscriptions(getUserId(user))) {
            getView().sendSources(user, getSourceDao().getSignedSources(getUserId(user)));
            updateUserState(user, UserState.PICK_SIGNED_SOURCE_TO_REMOVE);
        } else {
            sendUserHasNoSubscriptionsMessageToView(user);
        }
    }

    public void sendSubscriptionsToSetNotifyTiView(USER user, String notifyTime) {
        getUserDao().update(setNotifyTimeToUser(user, notifyTime));
        if (getUserDao().isThereSubscriptions(getUserId(user))) {
            getView().sendSources(user, getSourceDao().getSignedSources(getUserId(user)));
            updateUserState(user, UserState.PICK_SIGNED_SOURCE_TO_NOTIFY);
        } else {
            sendUserHasNoSubscriptionsMessageToView(user);
        }
    }

    public void sendTodayAndTomorrowScheduleToView(USER user, SOURCE source) {
        try {
            getView().sendTodayAndTomorrowSchedule(user, getParser().getTodayAndTomorrowSchedule(source));
            updateUserState(user, UserState.SCHEDULE);
        } catch (SourceWasntFoundError error) {
            sendThereIsNoSuchSubscriptionMessageToView(user);
        }
    }

    public void sendExtendedScheduleToView(USER user, SOURCE source) {
        try {
            getView().sendExtendedSchedule(user, getParser().getExtendedSchedule(source));
            updateUserState(user, UserState.SCHEDULE);
        } catch (SourceWasntFoundError error) {
            sendThereIsNoSuchSubscriptionMessageToView(user);
        }
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

    public void sendThereIsNoSuchSubscriptionMessageToView(USER user) {
        getView().sendThereIsNoSuchSubscriptionMessage(user);
    }

    public void sendThereIsAlreadySuchNotificationMessageToView(USER user) {
        getView().sendThereIsAlreadySuchNotificationMessage(user);
    }

    public void sendThereIsNoSuchSourceMessageToView(USER user) {
        getView().sendThereIsNoSuchSourceMessage(user);
    }

    public void sendSubscriptionSuccessMessageToView(USER user) {
        getView().sendSubscriptionSuccessMessage(user);
    }

    public void sendSubscriptionRemoveWasSuccessfulMessageToView(USER user) {
        getView().sendSubscriptionRemoveWasSuccessfulMessage(user);
    }

    public void sendUserHasNoSubscriptionsMessageToView(USER user) {
        getView().sendUserHasNoSubscriptionsMessage(user);
    }

    public void sendNotesToView(USER user) {
        if (getUserDao().isThereNotes(getUserId(user))) {
            getView().sendNotes(user, getUserDao().getNotes(getUserId(user)));
        } else {
            getView().sendEmptyNotesMessage(user);
        }
        updateUserState(user, UserState.NOTES);
    }

    public void sendEmptyNotesMessageToView(USER user) {
        getView().sendEmptyNotesMessage(user);
    }

    public void sendDeleteNotesToView(USER user) {
        getView().sendDeleteNotes(user);
    }

    protected abstract NOTE createNoteFromText(USER user, String noteText);

    public void addNoteToUser(USER user, String noteText) {
        getUserDao().addNoteToUser(createNoteFromText(user, noteText));
        getView().sendNoteWasAddedMessage(user);
    }

    public void sendSetNotifyMessageToView(USER user) {
        getView().sendSetNotifyMessage(user);
        updateUserState(user, UserState.NOTIFY);
    }

    public void sendNotifySuccessfullySetMessageToView(USER user) {
        getView().sendNotifySuccessfullySetMessage(user);
    }

    public void sendNotifyManageMessageToView(USER user) {
        getView().sendNotifyManageMessage(user, getUserDao().getUserNotifies(getUserId(user)));
    }

    public void sendClearNotesMessageToView(USER user) {
        getView().sendClearNotesMessage(user);
    }
}
