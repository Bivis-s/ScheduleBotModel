package by.bivis.schedule_bot_model.objects.parser_objects;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * The Schedule contains the list of days.
 * It's not obligatory the list from Monday to Sunday, there may be two weeks or more
 * For example, if the schedule has 2 weeks of 6 days, the length of the days list will be 12
 * Also, the schedule always should belong to any source
 */
@Data
public class Schedule {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private List<Day> days;
    private Source<?> source;
}
