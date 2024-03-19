package ru.malysheva.bookslibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.malysheva.bookslibrary.entity.Order;

import java.util.List;

/**
 * Интерфейс репозитория для работы с заказами.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Метод для поиска заказа по адресу электронной почты пользователя и идентификатору продукта.
     * @param userEmail адрес электронной почты пользователя
     * @param productId идентификатор продукта
     * @return заказ
     */
    Order findByUserEmailAndProductId(String userEmail, Long productId);

    /**
     * Метод для поиска всех заказов пользователя по его адресу электронной почты.
     * @param userEmail адрес электронной почты пользователя
     * @return список заказов
     */
    List<Order> findProductByUserEmail(String userEmail);

    /**
     * Метод для удаления всех заказов по идентификатору продукта.
     * @param productId идентификатор продукта
     */
    @Modifying
    @Query("delete from Order where product_id in :product_id")
    void deleteAllByProductId(@Param("product_id") Long productId);

}