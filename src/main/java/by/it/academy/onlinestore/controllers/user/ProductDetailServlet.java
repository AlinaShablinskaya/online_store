package by.it.academy.onlinestore.controllers.user;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "detail", urlPatterns = "/detail")
public class ProductDetailServlet extends HttpServlet {
    private final ProductService productService;

    public ProductDetailServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.productService = injector.getProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Integer productId = Integer.valueOf(req.getParameter("product_id"));
        req.setAttribute("product", productService.findProductById(productId));
        req.getRequestDispatcher("/single.jsp").forward(req, resp);
    }
}
