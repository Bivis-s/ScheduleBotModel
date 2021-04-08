package by.bivis.schedule_bot_model.controllers;

import by.bivis.schedule_bot_model.ScheduleBotModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class ScheduleBotController<USER, NEWS, SOURCE, SCHEDULE> {
    private final ScheduleBotModel<USER, NEWS, SOURCE, SCHEDULE> model;

    public abstract void handle(USER user, String command);
}
