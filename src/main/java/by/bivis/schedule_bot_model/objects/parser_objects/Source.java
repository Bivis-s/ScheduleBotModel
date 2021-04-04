package by.bivis.schedule_bot_model.objects.parser_objects;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Something that has a schedule
 */
@Data
public class Source<T extends Serializable> {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String category;
    private String content;
    private T primarySource; //TODO serialize primary source https://stackoverflow.com/questions/5966039/how-do-i-serialize-object-to-database-for-hibernate-to-read-in-java
}
