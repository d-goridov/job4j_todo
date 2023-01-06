package ru.job4j.todo.persistent;

import ru.job4j.todo.model.Priority;
import java.util.List;

/**
 * Интерфейс описывает бизнес логику приложения
 * по работе с приоритетом заданиями
 */
public interface PriorityRepository {

    /**
     * Метод получает список доступных приоритетов
     * @return - список
     */
    List<Priority> getAllPriority();

    /**
     * Метод осуществляет поиск приоритета по
     * идентификатору
     * @param id - идентификатор
     * @return - объект приоритета
     */
    Priority findById(int id);
}
