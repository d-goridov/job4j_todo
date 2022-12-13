package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistent.UserRepository;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    private final UserRepository repository;

    public SimpleUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> add(User user) {
        return repository.add(user);
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return repository.findUserByEmailAndPassword(email, password);
    }
}
