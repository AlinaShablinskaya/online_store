package by.it.academy.onlinestore.controllers.user;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.services.CatalogService;
import by.it.academy.onlinestore.services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "search", urlPatterns = "/searchCatalog")
public class SearchProductByCategoryServlet extends HttpServlet {
    private final ProductService productService;
    private final CatalogService catalogService;

    public SearchProductByCategoryServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.productService = injector.getProductService();
        this.catalogService = injector.getCatalogService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String group_name = req.getParameter("group_name");

        req.setAttribute("catalog", catalogService.showCatalog());
        req.setAttribute("products", productService.findAllByCatalogName(group_name));

        req.getRequestDispatcher("/searchCatalog.jsp").forward(req, resp);
    }
}
