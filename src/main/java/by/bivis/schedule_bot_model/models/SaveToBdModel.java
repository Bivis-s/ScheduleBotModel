package by.bivis.schedule_bot_model.models;

import by.bivis.schedule_bot_model.Parser;
import by.bivis.schedule_bot_model.objects.db_objects.NewsDao;
import by.bivis.schedule_bot_model.objects.db_objects.ScheduleDao;
import by.bivis.schedule_bot_model.objects.db_objects.SourceDao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class SaveToBdModel<NEWS, SOURCE, SCHEDULE> {
    private NewsDao<NEWS> newsDao;
    private SourceDao<SOURCE> sourceDao;
    private ScheduleDao<SCHEDULE> scheduleDao;
    private Parser<NEWS, SOURCE, SCHEDULE> parser;
}
