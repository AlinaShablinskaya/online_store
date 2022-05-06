package by.it.academy.onlinestore.controllers.admin;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.constants.Path;
import by.it.academy.onlinestore.constants.ServletContent;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(urlPatterns = "/update")
public class UpdateServlet extends HttpServlet {
    private final ProductService productService;
    private  int id;

    public UpdateServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.productService = injector.getProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        id = Integer.parseInt(req.getParameter(ServletContent.PRODUCT_ID));
        req.setAttribute(ServletContent.PRODUCT, productService.findProductById(id));
        req.getRequestDispatcher(Path.PATH_TO_ADMIN_UPDATE_PRODUCT_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String productName = req.getParameter(ServletContent.PRODUCT_NAME);
        final String brand = req.getParameter(ServletContent.BRAND);
        final String photo = req.getParameter(ServletContent.PHOTO);
        final String price = req.getParameter(ServletContent.PRICE);

        BigDecimal productPrice = BigDecimal.valueOf(Double.parseDouble(price));

        final Product product = Product.builder()
                .withId(id)
                .withProductName(productName)
                .withBrand(brand)
                .withPhoto(photo)
                .withPrice(productPrice)
                .build();

        productService.updateProduct(product);
        resp.sendRedirect(Path.URL_TO_PRODUCT_PANEL);
    }
}
