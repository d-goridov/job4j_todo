package ru.job4j.todo.persistent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

/**
 * Класс представляет собой реализацию хранилища пользователей,
 * в виде БД со взаимодествием через Hibernate
 */
@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {
    private static final String GET_BY_EMAIL_PASSWORD = "SELECT u FROM User AS u where u.email = :fEmail and "
            + "u.password = :fPassword";
    private final CrudRepositoryImpl crudRepository;

    @Override
    public Optional<User> add(User user) {
        crudRepository.run(session -> session.persist(user));
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return crudRepository.optional(GET_BY_EMAIL_PASSWORD, User.class,
                Map.of("fEmail", email, "fPassword", password));
    }
}
