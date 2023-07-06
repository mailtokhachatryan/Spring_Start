package am.myOffice.shopJDBC.exceptions;

import javax.servlet.http.HttpServlet;

public class ProductValidationException extends RuntimeException {
    public ProductValidationException(String message) {
        super(message);
    }
}
