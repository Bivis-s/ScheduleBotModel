package by.bivis.schedule_bot_model;

import by.bivis.schedule_bot_model.objects.parser_objects.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ScheduleBot<T extends User> implements Runnable {
    private final ScheduleBotController<T> controller;
}
