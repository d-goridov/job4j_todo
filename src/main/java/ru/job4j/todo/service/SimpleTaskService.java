package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.persistent.CategoryRepository;
import ru.job4j.todo.persistent.PriorityRepository;
import ru.job4j.todo.persistent.TaskRepository;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса по работе с заданиями
 */
@Service
public class SimpleTaskService implements TaskService {

    /**
     * Объект хранилища заданий
     */
    private final TaskRepository repository;
    /**
     * Объект хранилища приоритетов
     */
    private final PriorityRepository priorityRepository;
    /**
     * Объект хранилища категорий
     */
    private final CategoryRepository categoryRepository;

    public SimpleTaskService(TaskRepository repository, PriorityRepository priorityRepository,
                             CategoryRepository categoryRepository) {
        this.repository = repository;
        this.priorityRepository = priorityRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Task add(Task task, List<Integer> ids) {
        List<Category> categories = categoryRepository.findByIds(ids);
        task.setCategoryList(categories);
        return repository.add(task);
    }

    @Override
    public boolean update(Task task) {
        return repository.update(task);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public void complete(int id) {
        repository.complete(id);
    }

    @Override
    public Optional<Task> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Task> findTasks(boolean status) {
        return repository.findTasks(status);
    }

}
