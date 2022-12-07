package ru.job4j.todo.persistent;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * Реализация хранилища
 */
@Repository
@AllArgsConstructor
public class HbmStore implements Store {
    private final SessionFactory sf;


    @Override
    public Task add(Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        boolean rsl = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("update Task SET description = :fDescription, "
                    + "done = :fDone WHERE id = :fId");
            query.setParameter("fDescription", task.getDescription());
            query.setParameter("fDone", task.isDone());
            query.setParameter("fId", task.getId());
            rsl = query.executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return rsl;
    }

    public boolean delete(int id) {
        boolean rsl = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("DELETE Task WHERE id = :fId")
                    .setParameter("fId", id);
            rsl = query.executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return rsl;
    }

    @Override
    public Task findById(int id) {
        Task  task;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<Task> query = session.createQuery("SELECT t FROM Task as t WHERE id = :fId", Task.class)
                                       .setParameter("fId", id);
            task = query.uniqueResult();
            session.getTransaction().commit();
        }
        return task;
    }

    @Override
    public void complete(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("UPDATE Task SET done = :fDone WHERE id = :fId")
                    .setParameter("fDone", true)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
    }

    @Override
    public List<Task> findAll() {
        List<Task> rsl;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            rsl = session.createQuery("FROM Task").getResultList();
            session.getTransaction().commit();
        }
        return rsl;
    }

    @Override
    public List<Task> findTasks(boolean status) {
        List<Task> rsl;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            rsl = session.createQuery("select t FROM Task AS t WHERE t.done = :fDone")
                         .setParameter("fDone", status)
                         .getResultList();
            session.getTransaction().commit();
        }
        return rsl;
    }
}
