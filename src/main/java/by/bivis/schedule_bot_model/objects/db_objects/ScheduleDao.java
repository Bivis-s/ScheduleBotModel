package by.bivis.schedule_bot_model.objects.db_objects;

public interface ScheduleDao<SCHEDULE, SOURCE> extends BaseDao<SCHEDULE> {
    SCHEDULE get(SOURCE source);
}
