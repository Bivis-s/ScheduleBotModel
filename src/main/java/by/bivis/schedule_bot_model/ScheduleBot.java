package by.bivis.schedule_bot_model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ScheduleBot implements Runnable {
    private final ScheduleBotModel model;
    private final ScheduleBotController controller;
}
