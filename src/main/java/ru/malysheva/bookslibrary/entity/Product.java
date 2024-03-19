package ru.malysheva.bookslibrary.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Класс, представляющий сущность "Продукт" в базе данных (в контексте приложения для описания книг)
 */
@Entity
@Table(name = "product")
@Data
public class Product {

  /**
   * Уникальный идентификатор продукта.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

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
   * Общее количество копий продукта.
   */
  @Column(name = "copies")
  private int copies;

  /**
   * Количество доступных копий продукта.
   */
  @Column(name = "copies_available")
  private int copiesAvailable;

  /**
   * Категория продукта.
   */
  @Column(name = "category")
  private String category;

  /**
   * Путь к изображению продукта.
   */
  @Column(name = "img")
  private String img;
}