package ru.job4j.todo.persistent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmCategoryRepository implements CategoryRepository {

    private static final String GET_ALL = "FROM Category ORDER BY id";
    private static final String FIND_BY_ID = "SELECT c FROM Category AS c WHERE c.id = :fId";
    private static final String FIND_BY_IDS = "SELECT c FROM Category AS c where c.id IN :ids";
    private final CrudRepository crudRepository;

    @Override
    public List<Category> getAllCategory() {
        return crudRepository.getListResult(GET_ALL, Category.class);
    }

    @Override
    public Optional<Category> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Category.class, Map.of("fId", id));
    }

    @Override
    public List<Category> findByIds(List<Integer> ids) {
        return crudRepository.getListResult(FIND_BY_IDS, Map.of("ids", ids), Category.class);
    }


}

