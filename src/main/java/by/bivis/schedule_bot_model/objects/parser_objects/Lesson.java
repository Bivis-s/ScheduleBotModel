package by.bivis.schedule_bot_model.objects.parser_objects;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The lesson contains formatted text for easy reading
 * And if the lesson in the schedule no longer changes, it is probably approved.
 */
@Data
public class Lesson {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private boolean isApproved;
}
