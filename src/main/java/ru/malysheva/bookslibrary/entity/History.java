package ru.malysheva.bookslibrary.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Класс, представляющий сущность "История" в базе данных.
 */
@Entity
@Table(name = "history")
@Data
public class History {

    /**
     * Конструктор с параметрами.
     *
     * @param userEmail    Email пользователя.
     * @param orderDate    Дата заказа.
     * @param returnedDate Дата возврата.
     * @param title        Название продукта.
     * @param creator      Автор продукта.
     * @param description  Описание продукта.
     * @param img          Путь к изображению продукта.
     */
    public History(
            String userEmail, 
            String orderDate, 
            String returnedDate, 
            String title, 
            String creator,
            String description, 
            String img) {
        this.userEmail = userEmail;
        this.orderDate = orderDate;
        this.returnedDate = returnedDate;
        this.title = title;
        this.creator = creator;
        this.description = description;
        this.img = img;
    }

    /**
     * Конструктор без параметров.
     */
    public History() {
    }

    /**
     * Уникальный идентификатор записи истории.
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
     * Дата заказа.
     */
    @Column(name = "order_date")
    private String orderDate;

    /**
     * Дата возврата.
     */
    @Column(name = "returned_date")
    private String returnedDate;

    /**
     * Название продукта.
     */
    @Column(name = "title")
    private String title;

    /**
     * Автор продукта.
     */
    @Column(name = "creator")
    private String creator;

    /**
     * Описание продукта.
     */
    @Column(name = "description")
    private String description;

    /**
     * Путь к изображению продукта.
     */
    @Column(name = "img")
    private String img;
}