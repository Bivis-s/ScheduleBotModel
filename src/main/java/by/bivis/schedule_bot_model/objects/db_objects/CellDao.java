package by.bivis.schedule_bot_model.objects.db_objects;

import java.util.List;

public interface CellDao<T> extends BaseDao<T> {
    <F> List<F> getLessons(T cell);
}
