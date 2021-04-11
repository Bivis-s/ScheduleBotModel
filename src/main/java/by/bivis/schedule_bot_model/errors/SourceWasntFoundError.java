package by.bivis.schedule_bot_model.errors;

/**
 * The type Source was not found error.
 * Throws when DAO cannot find a Source in the database
 */
public class SourceWasntFoundError extends Error {
    public SourceWasntFoundError() {
    }

    public SourceWasntFoundError(String message) {
        super(message);
    }
}
