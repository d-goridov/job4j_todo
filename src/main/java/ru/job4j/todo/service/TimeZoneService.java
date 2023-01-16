package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.TimeZone;

public interface TimeZoneService {
    List<TimeZone> getAllTimeZones();
    List<Task> changeTimeZoneOfTasksForUser(List<Task> tasks, User user);
}
