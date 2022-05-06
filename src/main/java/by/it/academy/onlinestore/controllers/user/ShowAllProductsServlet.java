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

@WebServlet(urlPatterns = "/catalog")
public class ShowAllProductsServlet extends HttpServlet {
    private static final int ITEMS_PER_PAGE = 6;
    private final ProductService productService;
    private final CatalogService catalogService;

    public ShowAllProductsServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.productService = injector.getProductService();
        this.catalogService = injector.getCatalogService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int defaultPage = 1;
        String pageParameter = req.getParameter(ServletContent.PAGE);

        if (pageParameter != null) {
            defaultPage = Integer.parseInt(req.getParameter(ServletContent.PAGE));
        }

        int pageNumber = (defaultPage - 1) * ITEMS_PER_PAGE;

        req.setAttribute(ServletContent.PRODUCTS, productService.findAllProduct(pageNumber, ITEMS_PER_PAGE));
        req.setAttribute(ServletContent.CATALOG, catalogService.showCatalog());

        req.getRequestDispatcher(Path.PATH_TO_CATALOG_PAGE).forward(req, resp);
    }
}
