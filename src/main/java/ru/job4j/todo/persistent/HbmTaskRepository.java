package ru.job4j.todo.persistent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private static final String FIND_BY_ID = "SELECT t FROM Task AS t join fetch t.priority join fetch t.categoryList"
            + " WHERE t.id = :fId";
    private static final String SET_COMPLETE_TASK = "UPDATE Task SET done = :fDone WHERE id = :fId";
    private static final String FIND_BY_STATUS = "SELECT t FROM Task AS t join fetch t.priority "
            + "WHERE t.done = :fDone AND user_id = :fUser_id";
    private static final String FIND_ALL = "FROM Task AS t join fetch t.priority where user_id = :fUser_id";

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
    public Optional<Task> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Task.class, Map.of("fId", id));
    }

    @Override
    public void complete(int id) {
        crudRepository.run(SET_COMPLETE_TASK, Map.of("fDone", true,  "fId", id));
    }

    @Override
    public List<Task> findAll(User user) {
       return crudRepository.getListResult(FIND_ALL, Map.of("fUser_id", user.getId()), Task.class);
    }

    @Override
    public List<Task> findTasks(boolean status, User user) {
        return crudRepository.getListResult(FIND_BY_STATUS,
                Map.of("fDone", status, "fUser_id", user.getId()), Task.class);
    }
}
