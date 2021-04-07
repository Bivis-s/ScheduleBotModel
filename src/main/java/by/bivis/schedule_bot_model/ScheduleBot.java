package by.bivis.schedule_bot_model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ScheduleBot<USER, NEWS, SOURCE, SCHEDULE> implements Runnable {
    private final ScheduleBotController<USER, NEWS, SOURCE, SCHEDULE> controller;
}
