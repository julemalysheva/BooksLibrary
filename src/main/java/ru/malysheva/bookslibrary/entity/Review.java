package ru.malysheva.bookslibrary.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

/**
 * Класс, представляющий сущность "Отзыв" в базе данных.
 */
@Entity
@Table(name = "review")
@Data
public class Review {

  /**
   * Уникальный идентификатор отзыва.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * Email пользователя, оставившего отзыв.
   */
  @Column(name = "user_email")
  private String userEmail;

  /**
   * Дата создания отзыва.
   */
  @Column(name = "date")
  @CreationTimestamp
  private Date date;

  /**
   * Рейтинг, присвоенный продукту в отзыве.
   */
  @Column(name = "rating")
  private double rating;

  /**
   * Идентификатор продукта, к которому относится отзыв.
   */
  @Column(name = "product_id")
  private Long productId;

  /**
   * Текст отзыва.
   */
  @Column(name = "review_text")
  private String reviewDescription;

}
