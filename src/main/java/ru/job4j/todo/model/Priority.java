package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Модель данных "Приоритет задания"
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "priorities")
public class Priority {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private int id;

    /**
     * Название
     */
    @Column(name = "name")
    private String name;

    /**
     * Позиция
     */
    @Column(name = "position")
    private int position;
}
