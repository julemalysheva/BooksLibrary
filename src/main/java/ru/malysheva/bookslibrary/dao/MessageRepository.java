package ru.malysheva.bookslibrary.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;
import ru.malysheva.bookslibrary.entity.Message;

/**
 * Интерфейс репозитория для работы с сообщениями.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Метод для поиска сообщений по адресу электронной почты пользователя.
     *
     * @param userEmail адрес электронной почты пользователя
     * @param pageable  параметры страницы
     * @return страница сообщений
     */
    Page<Message> findByUserEmail(
        @RequestParam("user_email") String userEmail,
        Pageable pageable);

    /**
     * Метод для поиска сообщений по признаку закрытости.
     *
     * @param closed   признак закрытости сообщений
     * @param pageable параметры страницы
     * @return страница сообщений
     */
    Page<Message> findByClosed(
        @RequestParam("closed") boolean closed,
        Pageable pageable);    
}
