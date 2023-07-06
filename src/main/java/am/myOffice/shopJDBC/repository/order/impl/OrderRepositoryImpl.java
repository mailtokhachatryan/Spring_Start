package am.myOffice.shopJDBC.repository.order.impl;

import am.myOffice.shopJDBC.model.Order;
import am.myOffice.shopJDBC.repository.order.OrderRepository;
import am.myOffice.shopJDBC.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private final Connection connection;

    public OrderRepositoryImpl(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
        try {
            this.connection.createStatement().executeUpdate(
                    """
                            CREATE TABLE IF NOT EXISTS orders (
                            id bigserial primary key,
                            userId bigserial not null,
                            productId bigserial not null,
                            totalPrice double precision not null,
                            totalCountOfProduct int not null
                            )
                            """);
        } catch (SQLException e) {
            System.out.println("Connection exception");
        }
    }

    @Override
    public void create(Order order){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO orders (userId,productId,totalPrice,totalCountOfProduct) VALUES (?,?,?,?)"
            );
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getProductId());
            preparedStatement.setDouble(3, order.getTotalPrice());
            preparedStatement.setInt(4, order.getTotalCountOfProduct());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Order order){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE orders SET userId = ?, productId = ?, totalPrice = ?, totalCountOfProduct = ? WHERE id = ?");
            preparedStatement.setLong(1, order.getId());
            preparedStatement.setLong(2, order.getProductId());
            preparedStatement.setDouble(3, order.getTotalPrice());
            preparedStatement.setInt(4, order.getTotalCountOfProduct());
            preparedStatement.setLong(5, order.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Order getOrderById(Long id) {
        Order order = new Order();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("SELECT * from orders WHERE userId = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setOrderFields(order, resultSet);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public List<Order> getAll() {
        List<Order> ordersList = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = connection.createStatement().executeQuery("SELECT * from orders");
            addProductToListFromResultSet(ordersList, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }

    @Override
    public void delete(Long id) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("DELETE from orders WHERE id = ?");
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setOrderFields(Order order, ResultSet resultSet){
        try {
            order.setId(resultSet.getLong("id"));
            order.setUserId(resultSet.getLong("userId"));
            order.setProductId(resultSet.getLong("productId"));
            order.setTotalPrice(resultSet.getDouble("totalPrice"));
            order.setTotalCountOfProduct(resultSet.getInt("totalCountOfProduct"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addProductToListFromResultSet(List<Order> ordersList, ResultSet resultSet){
        while (true) {
            try {
                if (!resultSet.next()) break;
                Order order = new Order();
                setOrderFields(order, resultSet);
                ordersList.add(order);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
