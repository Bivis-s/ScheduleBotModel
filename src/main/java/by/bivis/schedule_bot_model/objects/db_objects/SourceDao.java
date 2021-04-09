package by.bivis.schedule_bot_model.objects.db_objects;

import java.util.List;

public interface SourceDao<SOURCE> extends BaseDao<SOURCE> {
    <SCHEDULE> SCHEDULE getSchedule(SOURCE source);

    // возвращает список источников, у которых хотя бы 1 подписка
    List<SOURCE> getSignedSources();

    // возвращает список источников, на которые подписан пользователь
    List<SOURCE> getSignedSources(long userId);

    // возвращает список источников, с определенным типом и подтипом
    List<SOURCE> getSourcesByCategoryAndSubcategory(String category, String subcategory);

    SOURCE getSelectedSource(long userId, String category, String subcategory, String sourceName);
}
