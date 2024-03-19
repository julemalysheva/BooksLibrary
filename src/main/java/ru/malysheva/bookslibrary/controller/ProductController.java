package ru.malysheva.bookslibrary.controller;

import org.springframework.web.bind.annotation.*;
import ru.malysheva.bookslibrary.config.Environment;
import ru.malysheva.bookslibrary.entity.Product;
import ru.malysheva.bookslibrary.responsemodels.ShelfProductResponseModel;
import ru.malysheva.bookslibrary.service.ProductService;
import ru.malysheva.bookslibrary.utils.JWTParser;

import java.util.List;

/**
 * Контроллер для операций с продуктами.
 */
@CrossOrigin(Environment.host)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    /**
     * Конструктор контроллера.
     *
     * @param productService сервис для операций с продуктами
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * PUT-запрос для заказа продукта.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
     * @return заказанный продукт
     * @throws Exception если заказ не удался
     */
    @PutMapping("/secure/order")
    public Product orderProduct(
        @RequestHeader(value = "Authorization") String token,
        @RequestParam Long productId) throws Exception {
            String userEmail = JWTParser.extractEmail(token);
            return productService.orderProduct(userEmail, productId);
        }

    /**
     * GET-запрос для проверки, заказан ли продукт пользователем.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
     * @return true, если продукт заказан пользователем, в противном случае false
     */
    @GetMapping("/secure/isorder/byuser")
    public Boolean orderProductByUser(
        @RequestHeader(value = "Authorization") String token,
        @RequestParam Long productId) {
            String userEmail = JWTParser.extractEmail(token);
        return productService.orderProductByUser(userEmail, productId);
    }

    /**
     * GET-запрос для получения текущего количества заказов пользователя.
     *
     * @param token токен авторизации
     * @return текущее количество заказов пользователя
     */
    @GetMapping("/secure/currentorder/count")
    public int currentOrderCount(
        @RequestHeader(value = "Authorization") String token) {
            String userEmail = JWTParser.extractEmail(token);
        return productService.currentOrderCount(userEmail);
    }

    /**
     * GET-запрос для получения списка продуктов на полке пользователя.
     *
     * @param token токен авторизации
     * @return список продуктов на полке пользователя
     * @throws Exception если не удалось получить список
     */
    @GetMapping("/secure/shelfproducts")
    public List<ShelfProductResponseModel> currentLoans(
        @RequestHeader(value = "Authorization") String token)
        throws Exception {
            String userEmail = JWTParser.extractEmail(token);
            return productService.currentProductsOnShelf(userEmail);
        }

    /**
     * GET-запрос для получения текущего количества продуктов на полке пользователя.
     *
     * @param token токен авторизации
     * @return текущее количество продуктов на полке пользователя
     */
    @GetMapping("/secure/shelfproducts/count")
    public int currentLoansCount(
        @RequestHeader(value = "Authorization") String token) {
            String userEmail = JWTParser.extractEmail(token);
            return productService.currentCountProductsOnShelf(userEmail);
        }

    /**
     * PUT-запрос для возврата продукта.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
     * @throws Exception если операция не удалась
     */
    @PutMapping("/secure/return")
    public void returnProduct(
        @RequestHeader(value = "Authorization") String token,
        @RequestParam Long productId) throws Exception {
            String userEmail = JWTParser.extractEmail(token);
            productService.returnProduct(userEmail, productId);
        }

    /**
     * PUT-запрос для продления аренды продукта.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
     * @throws Exception если операция не удалась
     */
    @PutMapping("/secure/renew")
    public void renewProduct(
        @RequestHeader(value = "Authorization") String token,
        @RequestParam Long productId) throws Exception {
            String userEmail = JWTParser.extractEmail(token);
            productService.renewProduct(userEmail, productId);
        }    
}