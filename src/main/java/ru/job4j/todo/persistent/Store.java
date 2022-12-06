package ru.job4j.todo.persistent;

import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * Интерфейс описывает функционал хранилища
 */
public interface Store {
    /**
     * Метод добавления задания
     * @param task - объект задание
     * @return - добавленное задание
     */
    Task add(Task task);

    /**
     * Метод обновляет информацию о задании
     * @param task - объект задание
     */
    void update(Task task);

    /**
     * Метод удаляет задание
     * @param id - объект задание
     */
    void delete(int id);

    /**
     * Метод выполняет поиск задания по
     * идентификатору
     * @param id - идентификатор задания
     * @return - объект типа Task
     */
    Task findById(int id);

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
    List<Task> findAll();

    /**
     * Метод выполняет поиск новых заданий
     * @return - список заданий
     */
    List<Task> findNewTasks();

    /**
     * Метод выполняет поиск завершенных
     * заданий
     * @return - список заданий
     */
    List<Task> findFinishedTasks();
}
