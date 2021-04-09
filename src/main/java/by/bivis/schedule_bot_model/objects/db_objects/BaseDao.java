package by.bivis.schedule_bot_model.objects.db_objects;

import java.util.List;

public interface BaseDao<T> {
    List<T> getAll();

    T get(long entityId);

    void save(T entity);

    void update(T entity);

    void delete(T entity);

    void saveOrUpdate(T entity);
}
