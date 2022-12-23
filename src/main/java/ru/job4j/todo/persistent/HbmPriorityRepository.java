package ru.job4j.todo.persistent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HbmPriorityRepository implements PriorityRepository {

    private static final String GET_ALL = "FROM Priority";
    private static final String FIND_BY_ID = "SELECT p FROM Priority AS p WHERE id = :fId";
    private final CrudRepository crudRepository;

    @Override
    public List<Priority> getAllPriority() {
        return crudRepository.getListResult(GET_ALL, Priority.class);
    }

    @Override
    public Priority findById(int id) {
        return crudRepository.getSingleResultObj(FIND_BY_ID, Priority.class, Map.of("fId", id));
    }
}
