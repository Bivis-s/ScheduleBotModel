package by.bivis.schedule_bot_model.views;

import java.util.List;
import java.util.Set;

public interface ScheduleBotView<USER, NEWS, SCHEDULE, SOURCE> {
    void sendNews(USER user, List<NEWS> news);

    void sendSourceCategories(USER user, Set<String> sources);

    void sendSourcesSubcategoryByCategory(USER user, Set<String> sourceSubcategories);

    void sendTodayAndTomorrowSchedule(USER user, SCHEDULE schedule);

    void sendExtendedSchedule(USER user, SCHEDULE schedule);

    void sendSources(USER user, List<SOURCE> sources);

    void sendInfoMessage(USER user);

    void sendParsingInProcessMessage(USER user);

    void sendThereIsAlreadySuchSubscriptionMessage(USER user);

    void sendThereIsNoSuchSubscriptionMessage(USER user);

    void sendThereIsNoSuchSourceMessage(USER user);

    void sendSubscriptionSuccessMessage(USER user);

    void sendSubscriptionRemoveWasSuccessfulMessage(USER user);

    void sendUserHasNoSubscriptionsMessage(USER user);
}
