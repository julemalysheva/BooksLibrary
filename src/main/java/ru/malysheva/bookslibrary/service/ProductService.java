package ru.malysheva.bookslibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.malysheva.bookslibrary.dao.*;
import ru.malysheva.bookslibrary.entity.*;
import ru.malysheva.bookslibrary.responsemodels.ShelfProductResponseModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Сервис для управления товарами.
 * Осуществляет операции заказа, возврата и продления товаров пользователем.
 */
@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private HistoryRepository historyRepository;

    /**
     * Конструктор для создания экземпляра сервиса товаров.
     * Осуществляет внедрение зависимостей от репозиториев товаров, заказов и истории.
     * @param productRepository репозиторий товаров
     * @param orderRepository репозиторий заказов
     * @param historyRepository репозиторий истории
     */
    public ProductService(
        ProductRepository productRepository, 
        OrderRepository orderRepository,
        HistoryRepository historyRepository) {
    this.productRepository = productRepository;
    this.orderRepository = orderRepository;
    this.historyRepository = historyRepository;
    }

    /**
     * Метод для оформления заказа на товар пользователем.
     * @param userEmail адрес электронной почты пользователя
     * @param productId идентификатор товара
     * @return заказанный товар
     * @throws Exception если товар не найден, товар уже заказан или нет доступных копий товара
     */
    public Product orderProduct(String userEmail, Long productId) 
        throws Exception {
      Optional<Product> product = productRepository.findById(productId);

      Order validate = orderRepository
        .findByUserEmailAndProductId(userEmail, productId);

        if (!product.isPresent()
            || validate != null
            || product.get().getCopiesAvailable() <= 0) {
           throw new Exception("Ошибка"); 
        }

        product.get().setCopiesAvailable(product.get().getCopiesAvailable() - 1);
        productRepository.save(product.get());

        Order order = new Order(
            userEmail,
            LocalDate.now().toString(),
            LocalDate.now().plusDays(7).toString(),
            product.get().getId());

        orderRepository.save(order);
        
        return product.get();
    }

    /**
     * Метод для проверки, заказал ли пользователь товар.
     * @param userEmail адрес электронной почты пользователя
     * @param productId идентификатор товара
     * @return true, если пользователь уже заказал товар, иначе - false
     */
    public Boolean orderProductByUser(String userEmail, Long productId) {
        Order validate = orderRepository
            .findByUserEmailAndProductId(userEmail, productId);
        return validate != null;
    }

    /**
     * Метод для получения текущего количества заказанных товаров пользователем.
     * @param userEmail адрес электронной почты пользователя
     * @return текущее количество заказанных товаров
     */
    public int currentOrderCount(String userEmail) {
        return orderRepository.findProductByUserEmail(userEmail).size();
    }

    /**
     * Метод для получения списка текущих товаров на полке пользователя.
     * @param userEmail адрес электронной почты пользователя
     * @return список товаров на полке пользователя
     * @throws Exception если товар или заказ не найдены
     */
    public List<ShelfProductResponseModel> currentProductsOnShelf(String userEmail)
        throws Exception {
            List<ShelfProductResponseModel> shelfResponses = new ArrayList<>();
            List<Order> orders = orderRepository.findProductByUserEmail(userEmail);
            List<Long> productsId = new ArrayList<>();

            for (Order order : orders) {
                productsId.add(order.getProductId());
            }

            List<Product> products = productRepository.findProductByProductIds(productsId);
            SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");

            for (Product product : products) {
                Optional<Order> order = orders
                .stream()
                .filter(x -> x.getProductId() == product.getId())
                .findFirst();

                if (order.isPresent()) {
                    
                    Date d1 = date.parse(order.get().getReturnDate());
                    Date d2 = date.parse(LocalDate.now().toString());

                    TimeUnit time = TimeUnit.DAYS;

                    long diff = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);
                    shelfResponses.add(
                        new ShelfProductResponseModel(product, (int) diff));
                }
            }
            return shelfResponses;
        }

    /**
     * Метод для получения текущего количества товаров на полке пользователя.
     * @param userEmail адрес электронной почты пользователя
     * @return текущее количество товаров на полке пользователя
     */
    public int currentCountProductsOnShelf(String userEmail) {
        return orderRepository.findProductByUserEmail(userEmail).size();
    }

    /**
     * Метод для возврата товара пользователем
     *  @param userEmail адрес электронной почты пользователя
     *  @param productId идентификатор возвращаемого товара
     *  @throws Exception если товар или заказ не найдены
     */
    public void returnProduct(String userEmail, Long productId) throws Exception {

        Optional<Product> product = productRepository.findById(productId);

        Order validateCheckout = orderRepository.findByUserEmailAndProductId(
            userEmail, 
            productId);

            if (!product.isPresent() || validateCheckout == null) {
                throw new Exception("Операция невозможна");
            }

            product.get().setCopiesAvailable(product.get().getCopiesAvailable() + 1);

            productRepository.save(product.get());
            orderRepository.deleteById(validateCheckout.getId());

            History history = new History(
                userEmail,
                validateCheckout.getOrderDate(),
                LocalDate.now().toString(),
                product.get().getTitle(),
                product.get().getCreator(),
                product.get().getDescription(),
                product.get().getImg());
            
            historyRepository.save(history);    
    }

    /**
     * Метод для продления аренды товара пользователем.
     * @param userEmail адрес электронной почты пользователя
     * @param productId идентификатор товара
     * @throws Exception если операция невозможна
     */
    public void renewProduct(String userEmail, Long productId) throws Exception {

        Order validate = orderRepository.findByUserEmailAndProductId(
            userEmail, 
            productId);

            if (validate == null) {
                throw new Exception("Операция невозможна");
            }

            SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
            
            Date d1 = date.parse(validate.getReturnDate());
            Date d2 = date.parse(LocalDate.now().toString());
            if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
                validate.setReturnDate(LocalDate.now().plusDays(3).toString());
                orderRepository.save(validate);
        }
    }
}
