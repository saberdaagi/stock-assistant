package com.stockassistant.ai.client.tools;

import com.stockassistant.ai.client.api.ProductsApi;
import com.stockassistant.ai.client.model.Product;
import com.stockassistant.ai.client.model.ProductRequest;
import com.stockassistant.ai.client.model.Products;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductTools {

    private final ProductsApi productsApi;

    @Tool(name = "create_product", description = "Create a new product in the inventory")
    public Product createProduct(
            @ToolParam(description = "Name of the product") String name,
            @ToolParam(description = "SKU code of the product") String sku,
            @ToolParam(description = "Price of the product") float price,
            @ToolParam(description = "Category of the product") ProductRequest.CategoryEnum category,
            @ToolParam(description = "Unit of measure (e.g., kg, pcs)") ProductRequest.UnitOfMeasureEnum unitOfMeasure
    ) throws RestClientException {
        log.info("Creating new product - name: {}, sku: {}, price: {}, category: {}, unitOfMeasure: {}",
                name, sku, price, category, unitOfMeasure);
        ProductRequest request = new ProductRequest();
        request.setName(name);
        request.setSku(sku);
        request.setPrice(price);
        request.setCategory(category);
        request.setUnitOfMeasure(unitOfMeasure);
        Product product = productsApi.createProduct(request);
        log.info("Product created successfully with UUID: {}", product.getUuid());
        return product;
    }

    @Tool(name = "delete_product", description = "Delete a product from the inventory by its UUID")
    public String deleteProduct(
            @ToolParam(description = "UUID of the product to delete") UUID uuid
    ) throws RestClientException {
        log.info("Deleting product with UUID: {}", uuid);
        productsApi.deleteProduct(uuid);
        log.info("Product deleted successfully");
        return "Product deleted successfully";
    }

    @Tool(name = "update_product", description = "Update the information of a product")
    public Product updateProduct(
            @ToolParam(description = "UUID of the product to update") UUID uuid,
            @ToolParam(description = "New name of the product") String name,
            @ToolParam(description = "New SKU of the product") String sku,
            @ToolParam(description = "New price of the product") float price,
            @ToolParam(description = "New category of the product") ProductRequest.CategoryEnum category,
            @ToolParam(description = "New unit of measure") ProductRequest.UnitOfMeasureEnum unitOfMeasure
    ) throws RestClientException {
        log.info("Updating product with UUID: {} - name: {}, sku: {}, price: {}, category: {}, unitOfMeasure: {}",
                uuid, name, sku, price, category, unitOfMeasure);
        ProductRequest request = new ProductRequest();
        request.setName(name);
        request.setSku(sku);
        request.setPrice(price);
        request.setCategory(category);
        request.setUnitOfMeasure(unitOfMeasure);
        Product product = productsApi.updateProduct(uuid, request);
        log.info("Product updated successfully");
        return product;
    }

    @Tool(
            name = "list_products",
            description = "Retrieve product details or search through the product catalog using any available filter such as name, SKU, category, price, or unit of measure. Use this whether the request is for a specific product (e.g., by name or SKU) or a broader list based on filters. This tool replaces both detailed and filtered product lookups."
    )
    public Products listProducts(
            @ToolParam(description = "Page number") Integer page,
            @ToolParam(description = "Page size") Integer pageSize,
            @ToolParam(description = "Sort by field") String sort,
            @ToolParam(description = "Filter by UUID") UUID uuid,
            @ToolParam(description = "Filter by SKU") String sku,
            @ToolParam(description = "Filter by name") String name,
            @ToolParam(description = "Filter by price") Float price,
            @ToolParam(description = "Filter by category") String category,
            @ToolParam(description = "Filter by unit of measure") String unitOfMeasure
    ) throws RestClientException {
        log.info("Listing products with filters - page: {}, pageSize: {}, sort: {}, uuid: {}, sku: {}, name: {}, price: {}, category: {}, unitOfMeasure: {}",
                page, pageSize, sort, uuid, sku, name, price, category, unitOfMeasure);
        Products products = productsApi.getProducts(page, pageSize, sort, uuid, sku, name, price, category, unitOfMeasure);
        log.info("Found {} products", products.getData().size());
        return products;
    }
}
