package ru.job4j.todo.util;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

public final class TimeZoneUtils {

    private TimeZoneUtils() {
    }

    public static List<Task> changeTimeZoneOfTasksForUser(List<Task> tasks, User user) {
        if (user.getTimeZone() == null) {
            user.setTimeZone(TimeZone.getDefault().getID());
        }
        for (Task task : tasks) {
            task.setCreated(task.getCreated().atZone(TimeZone.getDefault().toZoneId())
                    .withZoneSameInstant(ZoneId.of(user.getTimeZone())).toLocalDateTime());
        }
        return tasks;
    }
}
