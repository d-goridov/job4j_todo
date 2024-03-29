package ru.job4j.todo.persistent;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище заданий
 * @see ru.job4j.todo.model.Task
 */
public interface TaskRepository {
    /**
     * Метод добавления задания
     * @param task - объект задание
     * @return - добавленное задание
     */
    Task add(Task task);

    /**
     * Метод обновляет задание
     * @param task - объект задание
     * @return - true если обновление успешное
     * иначе - false
     */
    boolean update(Task task);

    /**
     * Метод удаляет задание
     * @param id - идентификатор задания
     * @return - true если удаление успешное
     * иначе - false
     */
    boolean delete(int id);

    /**
     * Метод выполняет поиск задания по
     * идентификатору
     * @param id - идентификатор задания
     * @return - объект типа Optional<Task>
     */
    Optional<Task> findById(int id);

    /**
     * Метод устанавливает заданию статус
     * Выполнено
     * @param id - идентификатор задания
     */
    void complete(int id);

    /**
     * Метод выполняет поиск всех заданий
     * @return - список заданий
     */
    List<Task> findAll(User user);

    /**
     * Метод выполняет заданий в зависимости
     * от стутуса выполнения
     * @return - список заданий
     */
    List<Task> findTasks(boolean status, User user);
}
