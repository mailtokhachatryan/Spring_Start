package am.myOffice.shopJDBC.service.order.impl;

import am.myOffice.shopJDBC.model.Order;
import am.myOffice.shopJDBC.repository.order.OrderRepository;
import am.myOffice.shopJDBC.repository.product.ProductRepository;
import am.myOffice.shopJDBC.repository.user.UserRepository;
import am.myOffice.shopJDBC.service.order.OrderService;
import am.myOffice.shopJDBC.util.DatabaseConnection;

import java.sql.SQLException;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DatabaseConnection databaseConnection;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            DatabaseConnection databaseConnection,
                            ProductRepository productRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.databaseConnection = databaseConnection;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createOrder(Long userId, Long productId, int count) throws SQLException {

        var connection = databaseConnection.getConnection();
        try {
            connection.setAutoCommit(false);

            var user = userRepository.get(userId);
            var product = productRepository.get(productId);
            var totalPrice = count * product.getPrice();
            Order order = new Order(userId, productId, totalPrice, count);

            orderRepository.create(order);
            var newBalance = user.getBalance() - totalPrice;
            user.setBalance(newBalance);
            userRepository.update(user);

            if (user.getBalance() < 0)
                throw new Exception("Your account doesn't have enough money!!!");
            System.out.println("Your order has been confirmed");

            connection.commit();
        } catch (Exception e){
            connection.rollback();
            System.out.println("Your account doesn't have enough money!!!");
        }
    }
}
