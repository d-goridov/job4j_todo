package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * Интерфейс описывает бизнес логику приложения
 */
public interface TaskService {
    /**
     * Метод добавления задания
     * @param task - объект задание
     * @return - добавленное задание
     */
    Task add(Task task);

    /**
     * Метод обновления задания
     * @param task - объект задание
     */
    void update(Task task);

    /**
     * Метод удаления задания
     * @param id - объект задание
     */
     void delete(int id);

    /**
     * Метод устанавливает статус задания
     * как "Выполнено"
     * @param id - идентификатор задания
     */
     void complete(int id);

    /**
     * Метод выполняет поиск задания по
     * идентификатору
     * @param id - идентификатор задания
     * @return - найденное задание
     */
    Task findById(int id);

    /**
     * Метод поиска всех заданий
     * @return - список заданий
     */
    List<Task> findAll();

    /**
     * Метод поиска новых заданий
     * @return - список заданий
     */
    List<Task> findNewTasks();

    /**
     * Метод поиска выполненных заданий
     * @return - список заданий
     */
    List<Task> findFinishedTasks();
}
