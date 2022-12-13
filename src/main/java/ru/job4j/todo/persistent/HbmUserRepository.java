package ru.job4j.todo.persistent;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {
    private final SessionFactory sf;
    private static final Logger LOG = LoggerFactory.getLogger(HbmUserRepository.class.getName());

    @Override
    public Optional<User> add(User user) {
        Optional<User> rsl = Optional.empty();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            rsl = Optional.of(user);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl;
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        Optional<User> user = Optional.empty();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("SELECT u FROM User AS u where u.email = :fEmail and " +
                    "u.password = :fPassword").setParameter("fEmail", email)
                                              .setParameter("fPassword", password);
            user = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }
}
