package ru.malysheva.bookslibrary.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;
import ru.malysheva.bookslibrary.entity.History;

/**
 * Интерфейс репозитория для работы с историей заказов.
 */
public interface HistoryRepository extends JpaRepository<History, Long> {
    /**
     * Метод для поиска истории заказов по адресу электронной почты пользователя.
     * @param email адрес электронной почты пользователя
     * @param pageable параметры страницы
     * @return страница истории заказов
     */
    Page<History> findProductByUserEmail(
        @RequestParam("email") String email,
        Pageable pageable);
}
