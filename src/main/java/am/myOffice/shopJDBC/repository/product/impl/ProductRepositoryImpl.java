package am.myOffice.shopJDBC.repository.product.impl;

import am.myOffice.shopJDBC.exceptions.ProductNotFoundException;
import am.myOffice.shopJDBC.exceptions.ProductValidationException;
import am.myOffice.shopJDBC.model.Product;
import am.myOffice.shopJDBC.repository.product.ProductRepository;
import am.myOffice.shopJDBC.util.DatabaseConnection;
import am.myOffice.shopJDBC.util.constants.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private final Connection connection;

    public ProductRepositoryImpl(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
        try {
            this.connection.createStatement().executeUpdate(
                    """
                            CREATE TABLE IF NOT EXISTS products (
                            id bigserial primary key,
                            name varchar(255) not null,
                            price double precision not null,
                            category varchar(255) not null,
                            isExists bool
                            )
                            """);
        } catch (SQLException e) {
            System.out.println("Connection exception");
        }

    }
    @Override
    public void create(Product product) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO products (name,price,category,isexists) VALUES (?,?,?,?)"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try{
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setBoolean(4, product.isExists());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            throw new ProductValidationException(Message.PRODUCT_CREATION_FAILED);
        }

    }

    @Override
    public void update(Product product, Long id) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE products SET name = ?, price = ?, category = ?, isexists = ? WHERE id = ?");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setBoolean(4, product.isExists());
            preparedStatement.setLong(5, id);

            var i = preparedStatement.executeUpdate();
            /*if (i == 0){
                throw new ProductNotFoundException(Message.PRODUCT_NOT_FOUND);
            }*/
            preparedStatement.close();
        } catch (Exception e) {
            throw new ProductValidationException(Message.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public Product get(Long id) {
        Product product = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement("SELECT * from products WHERE id = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = new Product();
                setProductFields(product, resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (product == null){
            throw new ProductNotFoundException(Message.PRODUCT_NOT_FOUND);
        }
        try {
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public List<Product> getAll() {
        List<Product> productsList = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = connection.createStatement().executeQuery("SELECT * from products");
            addProductToListFromResultSet(productsList, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productsList;
    }

    @Override
    public List<Product> findProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM products WHERE lower(name) LIKE lower(concat('%',?,'%'))"
            );
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            addProductToListFromResultSet(products, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from products WHERE id = ?");
            preparedStatement.setLong(1, id);
            var i = preparedStatement.executeUpdate();
            if (i == 0)
                throw new ProductValidationException(Message.PRODUCT_NOT_FOUND);
                preparedStatement.close();
        } catch (Exception e) {
            throw new ProductNotFoundException(Message.PRODUCT_NOT_FOUND);
        }

    }

    public List<String> getColumns() {
        List<String> attributes = new ArrayList<>();
        PreparedStatement preparedStatement;
        try {
            preparedStatement =
                    connection.prepareStatement("SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'products' ORDER BY ordinal_position");
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                attributes.add(resultSet.getString("column_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return attributes;
    }


    private void setProductFields(Product product, ResultSet resultSet){
        try {
            product.setId(resultSet.getLong("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            product.setCategory(resultSet.getString("category"));
            product.setExists(resultSet.getBoolean("isexists"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addProductToListFromResultSet(List<Product> productsList, ResultSet resultSet){
        while (true) {
            try {
                if (!resultSet.next()) break;
                Product product = new Product();
                setProductFields(product, resultSet);
                productsList.add(product);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
