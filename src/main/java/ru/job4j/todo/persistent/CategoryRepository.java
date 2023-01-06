package ru.job4j.todo.persistent;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс описывает бизнес логику приложения
 * по работе с категориями задания
 */
public interface CategoryRepository {

    /**
     * Метод для получения списка всех категорий
     * @return - список
     */
    List<Category> getAllCategory();

    /**
     * Метод осуществляет поиск категории по
     * идентификатору
     * @param id - идентификатор
     * @return - найденная категория
     */
    Optional<Category> findById(int id);

    /**
     * Метод для получения списка определенных
     * категорий
     * @param ids - список идентификаторов категорий
     * @return - список категорий
     */
    List<Category> findByIds(List<Integer> ids);
}
