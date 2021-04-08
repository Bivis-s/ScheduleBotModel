package by.bivis.schedule_bot_model.objects.db_objects;

public interface UserDao<USER> extends BaseDao<USER> {
    String getSelectedSourceCategory(long userId);

    String getSelectedSourceSubcategory(long userId);
}
