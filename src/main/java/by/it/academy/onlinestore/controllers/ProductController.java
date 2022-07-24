package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.product.ProductDto;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.mappers.ProductMapper;
import by.it.academy.onlinestore.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing product.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    /**
     * POST : create a new product
     * @param productDto the productDto to create
     * @return the new product in body
     */
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductDto saveProduct(@Valid  @RequestBody ProductDto productDto) {
        Product product = productService.addProduct(ProductMapper.INSTANCE.convertToProduct(productDto));
        return ProductMapper.INSTANCE.convertToProductDto(product);
    }

    /**
     * GET /{id} : get product by specified id
     * @param productId the id of the product to retrieve
     * @return the product with defined id in body
     */
    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable(value = "id") Integer productId) {
        Product product = productService.findProductById(productId);
        return ProductMapper.INSTANCE.convertToProductDto(product);
    }

    /**
     * GET : get all products on page
     * @param pageable the pageable display a chunk of data based on the page-number and page-size parameters specified
     * @return the list of products in body
     */
    @GetMapping("/")
    public List<ProductDto> showAllProductOnPage(Pageable pageable) {
        List<Product> products = productService.findAllProduct(pageable);
        return products.stream()
                .map(ProductMapper.INSTANCE::convertToProductDto)
                .collect(Collectors.toList());
    }

    /**
     * PUT : update an existing product
     * @param productDto the productDto to update product
     * @return updates product in body
     */
    @PutMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductDto updateProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productService.updateProduct(ProductMapper.INSTANCE.convertToProduct(productDto));
        return ProductMapper.INSTANCE.convertToProductDto(product);
    }

    /**
     * DELETE /{id} : delete product by specified id
     * @param productId the id of the product to delete
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProduct(@PathVariable(value = "id") Integer productId) {
        productService.removeProductById(productId);
    }

    /**
     * GET /{name}/products : get all products by specified category name
     * @param categoryName the catalog name to retrieve list of all products
     * @return the list of products in body
     */
    @GetMapping("/{name}/products")
    public List<ProductDto> showAllProductByCategory(@PathVariable(value = "name") String categoryName) {
        List<Product> products = productService.findAllByCatalogName(categoryName);
        return products.stream()
                .map(ProductMapper.INSTANCE::convertToProductDto)
                .collect(Collectors.toList());
    }

    /**
     * POST /{id}/product : create a new relationship between catalog and product
     * @param productId the id of the product to create
     * @param catalogId the id of the catalog to create
     */
    @PostMapping("/{id}/product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addProductToCatalog(@RequestParam("id") Integer productId, @PathVariable("id") Integer catalogId) {
        productService.addProductToCatalog(catalogId, productId);
    }

    /**
     * DELETE /{id}/product : delete relationship between catalog and product
     * @param productId the id of the product to delete relationship
     * @param catalogId the id of the catalog to delete relationship
     */
    @DeleteMapping("/{id}/product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProductFromCatalog(@RequestParam("id") Integer productId, @PathVariable("id") Integer catalogId) {
        productService.removeProductFromCatalog(catalogId, productId);
    }
}
