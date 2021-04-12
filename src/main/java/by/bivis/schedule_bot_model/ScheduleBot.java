package by.bivis.schedule_bot_model;

import by.bivis.schedule_bot_model.controllers.ScheduleBotController;
import lombok.RequiredArgsConstructor;

/**
 * The Schedule bot, the main class.
 * Implements the launch of the bot in the run() method
 *
 * @param <USER>     the user type parameter
 * @param <NEWS>     the news type parameter
 * @param <SOURCE>   the source type parameter
 * @param <SCHEDULE> the schedule type parameter
 */
@RequiredArgsConstructor
public abstract class ScheduleBot<USER, NEWS, SOURCE, SCHEDULE, NOTE> implements Runnable {
    private final ScheduleBotController<USER, NEWS, SOURCE, SCHEDULE, NOTE> controller;
}
