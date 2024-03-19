package ru.malysheva.bookslibrary.requestmodels;

import lombok.Data;

import java.util.Optional;

/**
 * Класс, представляющий запрос на добавление отзыва.
 */
@Data
public class ReviewRequestModel {
    private double rating;
    private Long productId;
    private Optional<String> reviewDescription;
}
