package ru.malysheva.bookslibrary.responsemodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.malysheva.bookslibrary.entity.Product;

/**
 * Класс, представляющий модель ответа о продукте на полке.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelfProductResponseModel {

    /**
     * Продукт на полке.
     */
    private Product product;
    /**
     * Время, оставшееся до возврата продукта.
     */
    private int timeLeft;


}
