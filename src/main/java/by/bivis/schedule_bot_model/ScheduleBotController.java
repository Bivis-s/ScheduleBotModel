package by.bivis.schedule_bot_model;

import by.bivis.schedule_bot_model.objects.parser_objects.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ScheduleBotController<T extends User> {
    private final ScheduleBotModel<T> scheduleBotModel;
}
