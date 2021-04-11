package by.bivis.schedule_bot_model.objects.db_objects;

public interface ScheduleDao<SCHEDULE, SOURCE> extends BaseDao<SCHEDULE> {

    /**
     * Returns the schedule by a source
     *
     * @param source the source
     * @return the schedule
     */
    SCHEDULE get(SOURCE source);
}
