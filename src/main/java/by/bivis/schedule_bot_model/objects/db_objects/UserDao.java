package by.bivis.schedule_bot_model.objects.db_objects;

import java.util.List;

public interface UserDao<USER, SOURCE, NOTE> extends BaseDao<USER> {
    String getSelectedSourceCategory(long userId);

    String getSelectedSourceSubcategory(long userId);

    void addSubscriptionToUser(long userId, SOURCE source);

    void removeSubscriptionFromUser(long userId, SOURCE source);

    boolean isThereSubscriptions(long userId);

    boolean isThereSuchSubscription(long userId, SOURCE source);

    boolean isThereNotes(long userId);

    boolean isThereNotification(long userId, String notifyTime, SOURCE source);

    List<NOTE> getNotes(long userId);

    void cleanNotes(long userId);

    void addNoteToUser(NOTE note);

    void addNotifyToUser(long userId, String notifyTime, SOURCE source);

    List<String> getUserNotifies(long userId);

    void clearNotifies(long userId);
}
