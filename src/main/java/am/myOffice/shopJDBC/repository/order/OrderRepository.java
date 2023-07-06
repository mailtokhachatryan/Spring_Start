package am.myOffice.shopJDBC.repository.order;

import am.myOffice.shopJDBC.model.Order;

import java.util.List;

public interface OrderRepository {


    void create(Order order);

    void update(Order order);

    Order getOrderById(Long id);

    List<Order> getAll();

    void delete(Long id);
}
