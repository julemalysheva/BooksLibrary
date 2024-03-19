package ru.malysheva.bookslibrary.service;

import org.springframework.stereotype.Service;
import ru.malysheva.bookslibrary.dao.OrderRepository;
import ru.malysheva.bookslibrary.dao.ProductRepository;
import ru.malysheva.bookslibrary.dao.ReviewRepository;
import ru.malysheva.bookslibrary.entity.Product;
import ru.malysheva.bookslibrary.requestmodels.AppendProductRequest;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Сервис для администратора, предоставляющий методы для управления товарами.
 */
@Service
@Transactional
public class AdminService {

    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;
    private OrderRepository orderRepository;

    /**
     * Конструктор для создания экземпляра сервиса администратора.
     * Осуществляет внедрение зависимостей репозиториев продуктов, отзывов и заказов.
     * @param productRepository репозиторий продуктов
     * @param reviewRepository репозиторий отзывов
     * @param orderRepository репозиторий заказов
     */
    public AdminService(
            ProductRepository productRepository,
            ReviewRepository reviewRepository,
            OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Метод для добавления нового продукта в базу данных.
     * @param product данные о продукте для добавления
     */
    public void postProduct(AppendProductRequest product) {
        Product item = new Product();
        item.setTitle(product.getTitle());
        item.setCreator(product.getCreator());
        item.setDescription(product.getDescription());
        item.setCopies(product.getCopies());
        item.setCopiesAvailable(product.getCopies());
        item.setCategory(product.getCategory());
        item.setImg(product.getImg());
        productRepository.save(item);
    }

    /**
     * Метод для удаления продукта из базы данных.
     * @param productId идентификатор продукта для удаления
     * @throws Exception если продукт не найден
     */
    public void deleteProduct(Long productId) throws Exception {
        Optional<Product> product = productRepository.findById(productId);

        if (!product.isPresent()) {
            throw new Exception("Товар отсутствует");
        }

        productRepository.delete(product.get());
        orderRepository.deleteAllByProductId(productId);
        reviewRepository.deleteAllByProductId(productId);
    }

    /**
     * Метод для увеличения количества доступных копий продукта.
     * @param productId идентификатор продукта
     * @throws Exception если продукт не найден
     */
    public void incProductCount(Long productId) throws Exception {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new Exception("Товар отсутствует");
        }

        product.get().setCopiesAvailable(product.get().getCopiesAvailable() + 1);
        product.get().setCopies(product.get().getCopies() + 1);

        productRepository.save(product.get());
    }

    /**
     * Метод для уменьшения количества доступных копий продукта.
     * @param productId идентификатор продукта
     * @throws Exception если продукт не найден или количество копий равно нулю
     */
    public void decProductCount(Long productId) throws  Exception {
        Optional<Product> product = productRepository.findById(productId);
        int count = product.get().getCopies();
        if (count == 0)
            return;;
        if (!product.isPresent()
        || product.get().getCopiesAvailable() <= 0
        || product.get().getCopies() <= 0) {
            throw new Exception("Товар отсутствует");
        }

        product.get().setCopiesAvailable(product.get().getCopiesAvailable() - 1);
        product.get().setCopies(count - 1);

        productRepository.save(product.get());
    }
}