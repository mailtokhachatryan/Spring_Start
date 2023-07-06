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

public class DeleteProductsServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProductRepository productRepository = new ProductRepositoryImpl(DatabaseConnection.getInstance());
        try {
            productRepository.delete(Long.parseLong(req.getParameter(Parameter.ID_PARAMETER)));
            List<Product> allProducts = productRepository.getAll();
            List<String> columns = productRepository.getColumns();
            req.setAttribute(Parameter.PRODUCTS_ATTRIBUTE,allProducts);
            req.setAttribute(Parameter.COLUMNS_ATTRIBUTE,columns);
            req.getRequestDispatcher(Path.PRODUCT_PATH).forward(req,resp);
        } catch (Exception e) {
            req.setAttribute(Parameter.MESSAGE_ATTRIBUTE,e.getMessage());
            req.getRequestDispatcher(Path.DELETE_PRODUCT_PATH).forward(req,resp);
        }
    }
}
