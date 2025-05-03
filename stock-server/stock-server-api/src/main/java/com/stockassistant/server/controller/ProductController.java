/**
 * Copyright (c) 2024 Stock Assistant. All rights reserved.
 *
 * This software is the confidential and proprietary information of the creator.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with
 * Stock Assistant.
 *
 * @author Daagi Saber
 * @version 1.0
 */

package com.stockassistant.server.controller;

import com.stockassistant.server.api.v1.model.ProductModel;
import com.stockassistant.server.api.v1.model.ProductRequestModel;
import com.stockassistant.server.api.v1.model.ProductsModel;
import com.stockassistant.server.api.v1.rest.ProductsApi;
import com.stockassistant.server.domain.feature.product.port.in.ProductUseCase;
import com.stockassistant.server.domain.model.Product;
import com.stockassistant.server.domain.model.ProductFilter;
import com.stockassistant.server.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

/**
 * Controller for handling product-related HTTP requests.
 * This controller implements the ProductsApi interface and provides endpoints for creating, deleting, retrieving, and updating products.
 */
@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class ProductController implements ProductsApi {

    /** The use case for product operations */
    private final ProductUseCase productUseCase;

    /** The mapper for converting between domain and API models */
    private final ProductMapper productMapper;

    /**
     * Creates a new product based on the provided request model.
     *
     * @param productRequestModel the request model containing product details
     * @return ResponseEntity containing the created product model and a location header
     */
    @Override
    public ResponseEntity<ProductModel> createProduct(ProductRequestModel productRequestModel) {
        log.info("Creating new product with SKU: {}", productRequestModel.getSku());
        Product createdProduct = productUseCase.create(productMapper.toProductRequest(productRequestModel));
        log.info("Product created successfully with UUID: {}", createdProduct.uuid());
        return ResponseEntity.created(URI.create("/products/" + createdProduct.uuid()))
                .body(productMapper.toModel(createdProduct));
    }

    /**
     * Deletes a product with the specified ID.
     *
     * @param id the UUID of the product to delete
     * @return ResponseEntity with no content
     */
    @Override
    public ResponseEntity<Void> deleteProduct(UUID id) {
        log.info("Deleting product with UUID: {}", id);
        productUseCase.delete(id);
        log.info("Product deleted successfully");
        return ResponseEntity.noContent()
                .build();
    }

    /**
     * Retrieves a list of products based on the provided filter criteria.
     *
     * @param page the page number (1-based)
     * @param pageSize the number of items per page
     * @param sort the field to sort by
     * @param uuid the UUID of the product to filter by
     * @param sku the SKU of the product to filter by
     * @param name the name of the product to filter by
     * @param price the price of the product to filter by
     * @param category the category of the product to filter by
     * @param unitOfMeasure the unit of measure of the product to filter by
     * @return ResponseEntity containing the list of products
     */
    @Override
    public ResponseEntity<ProductsModel> getProducts(Integer page,
                                                     Integer pageSize,
                                                     String sort,
                                                     UUID uuid,
                                                     String sku,
                                                     String name,
                                                     Float price,
                                                     String category,
                                                     String unitOfMeasure) {
        log.info("Fetching products with filters - page: {}, pageSize: {}, sort: {}, uuid: {}, sku: {}, name: {}, price: {}, category: {}, unitOfMeasure: {}",
                page, pageSize, sort, uuid, sku, name, price, category, unitOfMeasure);
        return ResponseEntity.ok(
                productMapper.toProducts(productUseCase.fetch(
                        ProductFilter.builder().pageRequest(PageRequest.of(
                                        page - 1,
                                        pageSize,
                                        Sort.by(sort)))
                                .uuid(uuid)
                                .sku(sku)
                                .name(name)
                                .category(category)
                                .price(price)
                                .unitOfMeasure(unitOfMeasure).build()))
        );
    }

    /**
     * Updates an existing product with the specified ID using the provided request model.
     *
     * @param id the UUID of the product to update
     * @param productInput the request model containing updated product details
     * @return ResponseEntity containing the updated product model
     */
    @Override
    public ResponseEntity<ProductModel> updateProduct(UUID id,
                                                      ProductRequestModel productInput) {
        log.info("Updating product with UUID: {}", id);
        ProductModel updatedProduct = productMapper.toModel(productUseCase.update(
                id,
                productMapper.toProductRequest(productInput)));
        log.info("Product updated successfully");
        return ResponseEntity.ok(updatedProduct);
    }
}
