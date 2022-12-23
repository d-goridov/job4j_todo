package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.persistent.PriorityRepository;

import java.util.List;

@Service
public class SimplePriorityService implements PriorityService {

    private final PriorityRepository repository;

    public SimplePriorityService(PriorityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Priority> getAll() {
        return repository.getAllPriority();
    }

    @Override
    public Priority findById(int id) {
        return repository.findById(id);
    }
}
