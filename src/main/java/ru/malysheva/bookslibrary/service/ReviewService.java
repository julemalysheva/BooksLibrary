package ru.malysheva.bookslibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.malysheva.bookslibrary.dao.ReviewRepository;
import ru.malysheva.bookslibrary.entity.Review;
import ru.malysheva.bookslibrary.requestmodels.ReviewRequestModel;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Сервис для работы с отзывами.
 */
@Service
@Transactional
public class ReviewService {
    
    private ReviewRepository reviewRepository;

    /**
     * Конструктор класса ReviewService.
     * @param reviewRepository репозиторий для работы с отзывами
     */
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Метод для добавления отзыва.
     * @param userEmail адрес электронной почты пользователя
     * @param reviewRequest модель запроса отзыва
     * @throws Exception если отзыв уже существует
     */
    public void postReview(
        String userEmail,
        ReviewRequestModel reviewRequest) throws Exception {
            Review validateReview = reviewRepository.findByUserEmailAndProductId(
                userEmail, reviewRequest.getProductId());

        if (validateReview != null) {
            throw new Exception("Повторная попытка");
        }                

        Review review = new Review();
        review.setProductId(reviewRequest.getProductId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        if (reviewRequest.getReviewDescription().isPresent()) {
            review.setReviewDescription(
                reviewRequest
                .getReviewDescription()
                .map(t -> t.toString())
                .orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
        }

    /**
     * Метод для проверки наличия отзыва пользователя о товаре.
     * @param userEmail адрес электронной почты пользователя
     * @param productId идентификатор товара
     * @return true, если отзыв существует, иначе false
     */
    public Boolean userReviewListed(String userEmail, Long productId) {
        return reviewRepository
        .findByUserEmailAndProductId(userEmail, productId) != null;
    }        
}