package ru.malysheva.bookslibrary.controller;

import org.springframework.web.bind.annotation.*;
import ru.malysheva.bookslibrary.config.Environment;
import ru.malysheva.bookslibrary.entity.Message;
import ru.malysheva.bookslibrary.responsemodels.AdminAnswer;
import ru.malysheva.bookslibrary.service.MessagesService;
import ru.malysheva.bookslibrary.utils.JWTParser;

/**
 * Контроллер для операций с сообщениями.
 */
@CrossOrigin(Environment.host)
@RestController
@RequestMapping("/api/messages")
public class MessagesController {
    
    private MessagesService messagesService;

    /**
     * Конструктор контроллера.
     *
     * @param messagesService сервис для операций с сообщениями
     */
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    /**
     * POST-запрос для отправки сообщения.
     *
     * @param token          токен авторизации
     * @param messageRequest данные сообщения
     */
    @PostMapping("/secure/send/message")
    public void postMessage(
        @RequestHeader(value = "Authorization") String token,
        @RequestBody Message messageRequest) {
            String userEmail = JWTParser.extractEmail(token);
            messagesService.postMessage(messageRequest, userEmail);
        }

    /**
     * PUT-запрос для ответа на сообщение от администратора.
     *
     * @param token  токен авторизации
     * @param answer ответ администратора на сообщение
     * @throws Exception если пользователь не является администратором
     */
    @PutMapping("/secure/admin/message")
    public void putMessage(
        @RequestHeader(value = "Authorization") String token,
        @RequestBody AdminAnswer answer) throws Exception {
            String email = JWTParser.extractEmail(token);
            String admin = JWTParser.extractRole(token);
            if (admin == null
                || !admin.equals("admin")) {
                throw new Exception("Страница только для админа");
            }
            messagesService.putMessage(answer, email);
        }    
}
