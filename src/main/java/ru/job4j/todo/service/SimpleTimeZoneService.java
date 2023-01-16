package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class SimpleTimeZoneService implements TimeZoneService {

    public List<TimeZone> getAllTimeZones() {
        var zones = new ArrayList<TimeZone>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        return zones;
    }



    @Override
    public List<Task> changeTimeZoneOfTasksForUser(List<Task> tasks, User user) {
        if(user.getTimeZone() == null) {
            user.setTimeZone(TimeZone.getDefault().getID());
        }
        for (Task task : tasks) {
            task.setCreated(task.getCreated().atZone(TimeZone.getDefault().toZoneId())
                    .withZoneSameInstant(ZoneId.of(user.getTimeZone())).toLocalDateTime());
        }
        return tasks;
    }
}
