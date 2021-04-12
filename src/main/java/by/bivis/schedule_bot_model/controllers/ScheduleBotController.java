package by.bivis.schedule_bot_model.controllers;

import by.bivis.schedule_bot_model.models.ScheduleBotModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class ScheduleBotController<USER, NEWS, SOURCE, SCHEDULE, NOTE> {
    private final ScheduleBotModel<USER, NEWS, SOURCE, SCHEDULE, NOTE> model;

    public abstract void handle(USER user, String command);

    public abstract void handleCommandByUserState(USER user, String command);
}
