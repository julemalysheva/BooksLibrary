package ru.malysheva.bookslibrary.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Класс, представляющий сущность "Сообщение" в базе данных.
 */
@Entity
@Table(name = "messages")
@Data
public class Message {

    /**
     * Конструктор без параметров.
     */
    public Message() {
    }

    /**
     * Конструктор с параметрами.
     * @param title Заголовок сообщения.
     * @param userText Текст сообщения от пользователя.
     */
    public Message(String title, String userText) {
        this.title = title;
        this.userText = userText;
    }

    /**
     * Уникальный идентификатор сообщения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Email пользователя.
     */
    @Column(name = "user_email")
    private String userEmail;

    /**
     * Заголовок сообщения.
     */
    @Column(name = "title")
    private String title;

    /**
     * Текст сообщения от пользователя.
     */
    @Column(name = "user_text")
    private String userText;

    /**
     * Email администратора, если сообщение было обработано администратором.
     */
    @Column(name = "admin_email")
    private String adminEmail;

    /**
     * Текст ответа от администратора.
     */
    @Column(name = "admin_text")
    private String adminText;

    /**
     * Флаг, указывающий на закрытие сообщения.
     */
    @Column(name = "closed")
    private boolean closed;
}
