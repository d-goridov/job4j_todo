package ru.job4j.todo.persistent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Map;

/**
 * Класс представляет собой реализацию хранилища заданий,
 * в виде БД со взаимодествием через Hibernate
 */
@Repository
@AllArgsConstructor
public class HbmTaskRepository implements TaskRepository {
    private static final String UPDATE = "UPDATE Task SET description = :fDescription, "
            + "done = :fDone, priority_id = :fPriority WHERE id = :fId";
    private static final String DELETE = "DELETE Task WHERE id = :fId";
    private static final String FIND_BY_ID = "SELECT t FROM Task AS t join fetch t.priority WHERE t.id = :fId";
    private static final String SET_COMPLETE_TASK = "UPDATE Task SET done = :fDone WHERE id = :fId";
    private static final String FIND_BY_STATUS = "SELECT t FROM Task AS t join fetch t.priority WHERE t.done = :fDone";
    private static final String FIND_ALL = "FROM Task AS t join fetch t.priority";

    private final CrudRepository crudRepository;

    @Override
    public Task add(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean update(Task task) {
        return crudRepository.queryGetBooleanResult(UPDATE,
                Map.of("fDescription", task.getDescription(), "fDone", task.isDone(),
                        "fPriority", task.getPriority().getId(), "fId", task.getId()));
    }

    public boolean delete(int id) {
        return crudRepository.queryGetBooleanResult(DELETE,
                Map.of("fId", id));
    }

    @Override
    public Task findById(int id) {
        return crudRepository.getSingleResultObj(FIND_BY_ID, Task.class, Map.of("fId", id));
    }

    @Override
    public void complete(int id) {
        crudRepository.run(SET_COMPLETE_TASK, Map.of("fDone", true,  "fId", id));
    }

    @Override
    public List<Task> findAll() {
       return crudRepository.getListResult(FIND_ALL, Task.class);
    }

    @Override
    public List<Task> findTasks(boolean status) {
        return crudRepository.getListResult(FIND_BY_STATUS, Map.of("fDone", status), Task.class);
    }
}
