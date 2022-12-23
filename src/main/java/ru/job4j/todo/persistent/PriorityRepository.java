package ru.job4j.todo.persistent;

import ru.job4j.todo.model.Priority;
import java.util.List;

public interface PriorityRepository {
    List<Priority> getAllPriority();
    Priority findById(int id);
}
