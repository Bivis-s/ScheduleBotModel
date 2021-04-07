package by.bivis.schedule_bot_model.objects.db_objects;

import java.util.List;

public interface ScheduleDao<SCHEDULE> extends BaseDao<SCHEDULE> {
    <COLUMN> List<COLUMN> getColumns(SCHEDULE schedule);

    <SOURCE> SCHEDULE get(SOURCE source);
}
