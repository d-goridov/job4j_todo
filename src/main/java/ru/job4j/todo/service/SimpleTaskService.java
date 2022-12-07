package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.persistent.Store;

import java.util.List;

/**
 * Реализация сервиса по работе с заданиями
 */
@Service
public class SimpleTaskService implements TaskService {

    /**
     * Объект хранилища
     */
    private final Store store;

    public SimpleTaskService(Store store) {
        this.store = store;
    }

    @Override
    public Task add(Task task) {
        return store.add(task);
    }

    @Override
    public boolean update(Task task) {
        return store.update(task);
    }

    @Override
    public boolean delete(int id) {
        return store.delete(id);
    }

    @Override
    public void complete(int id) {
        store.complete(id);
    }

    @Override
    public Task findById(int id) {
        return store.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return store.findAll();
    }

    @Override
    public List<Task> findTasks(boolean status) {
        return store.findTasks(status);
    }

}
