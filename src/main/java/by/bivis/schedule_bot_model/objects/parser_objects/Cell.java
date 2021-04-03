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
 * The cell is a certain period of time in which only one lesson can take place,
 * But there can be more lessons if they pass in parallel
 */
@Data
public class Cell {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private List<Lesson> lessons;
}
