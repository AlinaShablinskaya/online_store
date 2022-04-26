package by.it.academy.onlinestore.controllers.admin;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin/delete")
public class DeleteProductServlet extends HttpServlet {
    private final ProductService productService;

    public DeleteProductServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.productService = injector.getProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/admin/adminpanel.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        productService.removeProductById(id);
        resp.sendRedirect("/admin/adminpanel.jsp");
    }
}
