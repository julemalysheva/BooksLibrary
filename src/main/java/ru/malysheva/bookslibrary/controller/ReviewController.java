package ru.malysheva.bookslibrary.controller;

import org.springframework.web.bind.annotation.*;
import ru.malysheva.bookslibrary.config.Environment;
import ru.malysheva.bookslibrary.requestmodels.ReviewRequestModel;
import ru.malysheva.bookslibrary.service.ReviewService;
import ru.malysheva.bookslibrary.utils.JWTParser;

/**
 * Контроллер для операций с отзывами.
 */
@CrossOrigin(Environment.host)
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService reviewService;

    /**
     * Конструктор контроллера.
     *
     * @param reviewService сервис для операций с отзывами
     */
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * GET-запрос для проверки, оставлял ли пользователь отзыв о продукте.
     *
     * @param token     токен авторизации
     * @param productId идентификатор продукта
     * @return true, если пользователь оставлял отзыв о продукте, в противном случае false
     * @throws Exception если не удалось проверить отзыв
     */
    @GetMapping("/secure/user/product")
    public Boolean reviewProductByUser(
        @RequestHeader(value = "Authorization") String token,
        @RequestParam Long productId) throws Exception {
            String userEmail = JWTParser.extractEmail(token);
            if (userEmail == null) {
                throw new Exception("Ошибка пользователя");
            }
            return reviewService.userReviewListed(userEmail, productId);
    }

    /**
     * POST-запрос для добавления отзыва.
     *
     * @param token         токен авторизации
     * @param reviewRequest модель запроса отзыва
     * @throws Exception если не удалось добавить отзыв
     */
    @PostMapping("/secure")
    public void postReview(
        @RequestHeader(value = "Authorization") String token,
        @RequestBody ReviewRequestModel reviewRequest) throws Exception {
            String userEmail = JWTParser.extractEmail(token);
            if (userEmail == null) {
                throw new Exception("Ошибка пользователя");
            }
            reviewService.postReview(userEmail, reviewRequest);
    }    
}
