package by.bivis.schedule_bot_model.parser;

import java.util.List;

/**
 * The Parser interface that returns objects from the college website, etc.
 *
 * @param <NEWS>     the news type parameter
 * @param <SOURCE>   the source type parameter
 * @param <SCHEDULE> the schedule type parameter
 */
public interface Parser<NEWS, SOURCE, SCHEDULE> {
    /**
     * Gets news.
     *
     * @return the news
     */
    List<NEWS> getNews();

    /**
     * Gets today and tomorrow schedule.
     *
     * @param source the source
     * @return the today and tomorrow schedule
     */
    SCHEDULE getTodayAndTomorrowSchedule(SOURCE source);

    /**
     * Gets extended schedule.
     *
     * @param source the source
     * @return the extended schedule
     */
    SCHEDULE getExtendedSchedule(SOURCE source);

    /**
     * Gets sources.
     *
     * @return the sources
     */
    List<SOURCE> getSources();
}
