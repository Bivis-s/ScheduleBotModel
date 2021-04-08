package by.bivis.schedule_bot_model.views;

import java.util.List;
import java.util.Set;

public interface ScheduleBotView<USER, NEWS, SCHEDULE, SOURCE> {
    void sendNews(USER user, List<NEWS> news);

    void sendSourceCategories(USER user, Set<String> sources);

    void sendSourcesSubcategoryByCategory(USER user, Set<String> sourceSubcategories);

    void sendSignedSources(USER user, List<SOURCE> sources);

    void sendSchedule(USER user, SCHEDULE schedule);

    void sendSources(USER user, List<SOURCE> sources);

    void sendMessage(USER user, String message);

    void sendParsingInProcessMessage(USER user, String message);
}
