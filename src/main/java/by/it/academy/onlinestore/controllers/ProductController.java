package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.ProductDto;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.mappers.ProductMapper;
import by.it.academy.onlinestore.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductDto saveProduct(@RequestBody ProductDto productDto) {
        Product product = productService.addProduct(ProductMapper.INSTANCE.convertToProduct(productDto));
        return ProductMapper.INSTANCE.convertToProductDto(product);
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable(value = "id") Integer productId) {
        Product product = productService.findProductById(productId);
        return ProductMapper.INSTANCE.convertToProductDto(product);
    }

    @GetMapping("/")
    public List<ProductDto> showAllProduct() {
        List<Product> products = productService.findAllProduct();
        return products.stream()
                .map(ProductMapper.INSTANCE::convertToProductDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{size}/page")
    public List<ProductDto> showAllProductOnPage(@RequestParam("page") int page, @PathVariable(value = "size") int size) {
        List<Product> products = productService.findAllProduct(page, size);
        return products.stream()
                .map(ProductMapper.INSTANCE::convertToProductDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        Product product = productService.updateProduct(ProductMapper.INSTANCE.convertToProduct(productDto));
        return ProductMapper.INSTANCE.convertToProductDto(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProduct(@PathVariable(value = "id") Integer productId) {
        productService.removeProductById(productId);
    }

    @GetMapping("/{name}/products")
    public List<ProductDto> showAllProductByCategory(@PathVariable(value = "name") String categoryName) {
        List<Product> products = productService.findAllByCatalogName(categoryName);
        return products.stream()
                .map(ProductMapper.INSTANCE::convertToProductDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addProductToCatalog(@RequestParam("id") Integer productId, @PathVariable("id") Integer catalogId) {
        productService.addProductToCatalog(catalogId, productId);
    }

    @DeleteMapping("/{id}/product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProductFromCatalog(@RequestParam("id") Integer productId, @PathVariable("id") Integer catalogId) {
        productService.removeProductFromCatalog(catalogId, productId);
    }
}
