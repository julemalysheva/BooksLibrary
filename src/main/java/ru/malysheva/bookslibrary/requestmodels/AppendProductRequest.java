package ru.malysheva.bookslibrary.requestmodels;

import lombok.Data;

/**
 * Класс, представляющий запрос на добавление продукта.
 */
@Data
public class AppendProductRequest {
    private  String title;
    private  String creator;
    private String description;
    private int copies;
    private  String category;
    private String img;
}
