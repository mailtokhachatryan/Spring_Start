package am.myOffice.shopJDBC.controller.Product;

import am.myOffice.shopJDBC.model.Product;
import am.myOffice.shopJDBC.repository.product.ProductRepository;
import am.myOffice.shopJDBC.repository.product.impl.ProductRepositoryImpl;
import am.myOffice.shopJDBC.util.DatabaseConnection;
import am.myOffice.shopJDBC.util.constants.Parameter;
import am.myOffice.shopJDBC.util.constants.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CreateProductServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductRepository productRepository = new ProductRepositoryImpl(DatabaseConnection.getInstance());
        Product product = new Product();

        try {
            product.setName(req.getParameter(Parameter.NAME_PARAMETER));
            product.setPrice(Double.parseDouble(req.getParameter(Parameter.PRICE_PARAMETER)));
            product.setCategory(req.getParameter(Parameter.CATEGORY_PARAMETER));
            product.setExists(Boolean.parseBoolean(req.getParameter(Parameter.IS_EXISTS_PARAMETER)));

            productRepository.create(product);
            List<Product> allProducts = productRepository.getAll();
            List<String> columns = productRepository.getColumns();

            req.setAttribute(Parameter.PRODUCTS_ATTRIBUTE,allProducts);
            req.setAttribute(Parameter.COLUMNS_ATTRIBUTE,columns);
            req.getRequestDispatcher(Path.PRODUCT_PATH).forward(req,resp);
        } catch (Exception e) {
            req.setAttribute(Parameter.MESSAGE_ATTRIBUTE,e.getMessage());
            req.getRequestDispatcher(Path.CREATE_PRODUCT_PATH).forward(req,resp);
        }
    }
}
