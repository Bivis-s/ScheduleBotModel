package by.bivis.schedule_bot_model.objects.db_objects;

import java.util.List;
import java.util.Set;

public interface SourceDao<SOURCE> extends BaseDao<SOURCE> {

    /**
     * Returns the source list that the user is subscribed to
     *
     * @param userId the user id
     * @return the signed sources
     */
    List<SOURCE> getSignedSources(long userId);

    /**
     * Returns the source list with specific type and subtype
     *
     * @param category    the category
     * @param subcategory the subcategory
     * @return the sources by category and subcategory
     */
    List<SOURCE> getSourcesByCategoryAndSubcategory(String category, String subcategory);

    /**
     * Returns the source the user has selected in the chat
     *
     * @param userId      the user id
     * @param category    the category
     * @param subcategory the subcategory
     * @param sourceName  the source name
     * @return the selected source
     */
    SOURCE getSelectedSource(long userId, String category, String subcategory, String sourceName);

    /**
     * Returns the source by name, it will be ambiguous if there are several sources with the same name in the db
     *
     * @param sourceName the source name
     * @return the source by name
     */
    SOURCE getSourceByName(String sourceName);

    /**
     * Returns the source by name (from subscriptions)
     *
     * @param userId     the user id
     * @param sourceName the source name
     * @return the source from subscriptions by name
     */
    SOURCE getSourceFromSubscriptionsByName(long userId, String sourceName);

    /**
     * Returns the source category set (for example teachers, subjects, etc.)
     *
     * @return the source category set
     */
    Set<String> getSourceCategorySet();

    /**
     * Returns the source subcategory set (for example first letters of teachers category)
     *
     * @param category the category
     * @return the source subcategory list
     */
    Set<String> getSourceSubcategoryList(String category);

    /**
     * Returns the source set by category and subcategory
     *
     * @param category    the category
     * @param subcategory the subcategory
     * @return the sources
     */
    Set<SOURCE> getSources(String category, String subcategory);
}
