package ru.malysheva.bookslibrary.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Класс, представляющий сущность "Заказ" в базе данных.
 */
@Entity
@Table(name = "product_order")
@Data
public class Order {

    /**
     * Конструктор без параметров.
     */
    public Order() {
    }

    /**
     * Конструктор с параметрами.
     *
     * @param userEmail  Email пользователя.
     * @param orderDate  Дата заказа.
     * @param returnDate Дата возврата заказа.
     * @param productId  Идентификатор продукта.
     */
    public Order(
        String userEmail,
        String orderDate,
        String returnDate,
        Long productId) {
      this.userEmail = userEmail;
      this.orderDate = orderDate;
      this.returnDate = returnDate;
      this.productId = productId;                
    }

    /**
     * Уникальный идентификатор заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Email пользователя, сделавшего заказ.
     */
    @Column(name = "user_email")
    private String userEmail;

    /**
     * Дата заказа.
     */
    @Column(name = "order_date")
    private String orderDate;

    /**
     * Дата возврата заказа.
     */
    @Column(name = "return_date")
    private String returnDate;

    /**
     * Идентификатор продукта, на который сделан заказ.
     */
    @Column(name = "product_id")
    private Long productId;
}
