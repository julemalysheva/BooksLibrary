package ru.malysheva.bookslibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.malysheva.bookslibrary.dao.MessageRepository;
import ru.malysheva.bookslibrary.entity.Message;
import ru.malysheva.bookslibrary.responsemodels.AdminAnswer;

import java.util.Optional;

/**
 * Сервис для управления сообщениями.
 * Осуществляет операции создания и обновления сообщений в хранилище данных.
 */
@Service
@Transactional
public class MessagesService {
    
    private MessageRepository messageRepository;

    /**
     * Конструктор для создания экземпляра сервиса сообщений.
     * Осуществляет внедрение зависимости от репозитория сообщений.
     *
     * @param messageRepository репозиторий сообщений
     */
    public MessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Метод для создания нового сообщения в системе.
     *
     * @param messageRequest запрос на создание сообщения
     * @param userEmail      адрес электронной почты пользователя, создавшего сообщение
     */
    public void postMessage(Message messageRequest, String userEmail) {
        Message message = new Message(
            messageRequest.getTitle(),
            messageRequest.getUserText());
        message.setUserEmail(userEmail);
        messageRepository.save(message);    
    }

    /**
     * Метод для обновления сообщения администратором.
     *
     * @param adminQuestionRequest запрос на обновление сообщения администратором
     * @param userEmail            адрес электронной почты администратора
     * @throws Exception если сообщение не найдено
     */
    public void putMessage(
        AdminAnswer adminQuestionRequest,
        String userEmail) throws Exception {
            Optional<Message> message = messageRepository.findById(
                adminQuestionRequest.getId());

            if (!message.isPresent()) {
                throw new Exception("Ошибка сообщений");
            }

            message.get().setAdminEmail(userEmail);
            message.get().setAdminText(adminQuestionRequest.getText());
            message.get().setClosed(true);
            messageRepository.save(message.get());
        }
}