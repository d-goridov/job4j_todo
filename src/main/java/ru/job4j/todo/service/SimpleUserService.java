package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistent.UserRepository;

import java.util.Optional;

/**
 * Класс описывает бизнес-логику приложения по
 * работе с пользователем. Является потокобезопасным,
 * т.к. проблема возникающая при добавлении одинаковых
 * пользователей в методе add() решена на уровне БД c помощью unique
 * constraint поля email у сущности User.
 * @author Dmitriy Goridov
 * @version 1.0
 */

@ThreadSafe
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

    @Override
    public boolean update(User user) {
        return repository.update(user);
    }
}
