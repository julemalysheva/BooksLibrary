package ru.malysheva.bookslibrary.responsemodels;

import lombok.Data;

/**
 * Класс, представляющий ответ администратора.
 */
@Data
public class AdminAnswer {
    private Long id;
    private String text;
}
