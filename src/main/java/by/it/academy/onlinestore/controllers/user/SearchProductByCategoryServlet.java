package by.it.academy.onlinestore.controllers.user;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.constants.Path;
import by.it.academy.onlinestore.constants.ServletContent;
import by.it.academy.onlinestore.services.CatalogService;
import by.it.academy.onlinestore.services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/searchCatalog")
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
        final String group_name = req.getParameter(ServletContent.GROUP_NAME);

        req.setAttribute(ServletContent.CATALOG, catalogService.showCatalog());
        req.setAttribute(ServletContent.PRODUCTS, productService.findAllByCatalogName(group_name));

        req.getRequestDispatcher(Path.PATH_TO_SEARCH_CATALOG_PAGE).forward(req, resp);
    }
}
