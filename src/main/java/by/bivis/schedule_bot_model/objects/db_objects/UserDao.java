package by.bivis.schedule_bot_model.objects.db_objects;

public interface UserDao<USER, SOURCE> extends BaseDao<USER> {
    String getSelectedSourceCategory(long userId);

    String getSelectedSourceSubcategory(long userId);

    void addSubscriptionToUser(long userId,  SOURCE source);

    boolean isThereSuchSubscription(long userId,  SOURCE source);
}
