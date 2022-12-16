package ru.job4j.todo.persistent;

import org.hibernate.Session;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface CrudRepository {
    <T> T exucuteCommand(Function<Session, T> command);
    void run(Consumer<Session> command);
    void run(String query, Map<String, Object> args);
    <T> T getSingleResultObj(String query, Class<T> cl, Map<String, Object> args);
    <T> boolean queryGetBooleanResult(String query, Map<String, Object> args);
    <T> List<T> getListResult(String query, Class<T> cl);
    <T> List<T> getListResult(String query, Map<String, Object> args, Class<T> cl);
    <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args);

}
