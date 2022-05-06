package by.it.academy.onlinestore.controllers.admin;

import by.it.academy.onlinestore.ApplicationInjector;
import by.it.academy.onlinestore.constants.ServletContent;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.services.CatalogService;
import by.it.academy.onlinestore.constants.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/addCatalog")
public class AddCatalogServlet extends HttpServlet {
    private final CatalogService catalogService;

    public AddCatalogServlet() {
        ApplicationInjector injector = ApplicationInjector.getInstance();
        this.catalogService = injector.getCatalogService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.PATH_TO_ADD_CATALOG_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String groupName = req.getParameter(ServletContent.GROUP_NAME);

        final Catalog catalog = Catalog.builder()
                .withGroupName(groupName)
                .build();

        catalogService.createNewCatalog(catalog);
        resp.sendRedirect(Path.URL_TO_PRODUCT_PANEL);
    }
}
