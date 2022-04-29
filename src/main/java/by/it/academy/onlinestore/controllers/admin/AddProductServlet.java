package by.it.academy.onlinestore.controllers.admin;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/addproduct")
public class AddProductServlet extends HttpServlet {
    private final ProductService productService;

    public AddProductServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.productService = injector.getProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/admin/addproduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String productName = req.getParameter("productName");
        final String brand = req.getParameter("brand");
        final String photo = req.getParameter("photo");
        final String price = req.getParameter("price");

        Integer productPrice = Integer.valueOf(price);

        final Product product = Product.builder()
                .withProductName(productName)
                .withBrand(brand)
                .withPhoto(photo)
                .withPrice(productPrice)
                .build();

        productService.addProduct(product);
        resp.sendRedirect("/showProducts.jsp");
    }
}
