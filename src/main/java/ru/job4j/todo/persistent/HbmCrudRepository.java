package ru.job4j.todo.persistent;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor
@Repository
public class HbmCrudRepository implements CrudRepository {

    /**
     * Логгер
     */
    private final static Logger LOG = LoggerFactory.getLogger(HbmCrudRepository.class.getName());

    /**
     * Объект для получения сессии к БД
     */
    private final SessionFactory sf;

    /**
     * Метод выполняет абстрактную операцию. Он принимает некую "команду",
     * открывает сессию, начинает транзакцию и выполняет эту команду.
     * Команда принимается в виде объекта функционального интерфейса,
     * благодаря чему достигается абстрактность операции.
     * @param command - команда, которую необходимо выполнить
     * @param <T> - обобщенный тип
     * @return - значение типа T
     */
    public <T> T exucuteCommand(Function<Session, T> command) {
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Метод принимает как параметр команду
     * и передает ее на выполнение в
     * метод exucuteCommand(Function<Session, T> command)
     * @param command - команда
     */
    public void run(Consumer<Session> command) {
        exucuteCommand(session -> {
                    command.accept(session);
                    return null;
                }
        );
    }

    /**
     * Метод принимает параметры, создает команду,
     * делегирует на выполнение методу run(Consumer<Session> command)
     * @param query -HQL запрос в виде строки
     * @param args - параметры запроса, в виде карты, где key - псевдоним
     *               value - значение
     */
    public void run(String query, Map<String, Object> args) {
        Consumer<Session> command = session -> {
            var sq = session.createQuery(query);
            for (Map.Entry<String, Object> arg: args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            sq.executeUpdate();
        };
        run(command);
    }

    /**
     * Метод принимает параметры, создает команду,
     * передает на выполнение в метод exucuteCommand(Function<Session, T> command)
     * @param query - HQL запрос в виде строки
     * @param cl - класс, объект которого мы получаем
     * @param args - параметры запроса, в виде карты, где
     *        key - псевдоним, value - значение
     * @return - объект типа T
     */
    public <T> T getSingleResultObj(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, T> command = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg: args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.getSingleResult();
        };
        return exucuteCommand(command);
    }

    /**
     * Метод принимает параметры, создает команду,
     * передает на выполнение в метод exucuteCommand(Function<Session, T> command)
     * @param query - HQL запрос в виде строки
     * @param args - параметры запроса, в виде карты, где
     *        key - псевдоним, value - значение
     * @return - true - если команда выполнена успешно, иначе - false
     */
    public <T> boolean queryGetBooleanResult(String query, Map<String, Object> args) {
        Function<Session, Boolean> command = session -> {
            var sq = session.createQuery(query);
            for (Map.Entry<String, Object> arg: args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.executeUpdate() > 0;
        };
        return exucuteCommand(command);
    }

    /**
     * Метод принимает параметры, создает команду,
     * передает на выполнение в метод exucuteCommand(Function<Session, T> command)
     * @param query - HQL запрос в виде строки
     * @param cl - класс, объект которого мы получаем
     * @return - список объектов типа T
     */
    public <T> List<T> getListResult(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, cl)
                .list();
        return exucuteCommand(command);
    }

    /**
     * Метод принимает параметры, создает команду,
     * передает на выполнение в метод exucuteCommand(Function<Session, T> command)
     * @param query - HQL запрос в виде строки
     * @param args - параметры запроса, в виде карты, где
     *               key - псевдоним, value - значение
     * @param cl - класс, объект которого мы получаем
     * @return - список объектов типа T
     */
    public <T> List<T> getListResult(String query, Map<String, Object> args, Class<T> cl) {
        Function<Session, List<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg: args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.list();
        };
        return exucuteCommand(command);
    }

    /**
     * Метод принимает параметры, создает команду,
     * передает на выполнение в метод exucuteCommand(Function<Session, T> command)
     * @param query - HQL запрос в виде строки
     * @param args - параметры запроса, в виде карты, где
     *               key - псевдоним, value - значение
     * @param cl - класс, объект которого мы получаем
     * @return - Optional<T>
     */
    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg: args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return Optional.ofNullable(sq.getSingleResult());
        };
        return exucuteCommand(command);
    }
}
