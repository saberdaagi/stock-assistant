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

package com.stockassistant.server.domain.feature.product;

import com.stockassistant.server.domain.feature.product.port.in.ProductUseCase;
import com.stockassistant.server.domain.feature.product.port.out.ProductRepositoryPort;
import com.stockassistant.server.domain.model.Product;
import com.stockassistant.server.domain.model.ProductFilter;
import com.stockassistant.server.domain.model.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for handling product-related business logic.
 * This service implements the ProductUseCase interface and provides methods for
 * managing products in the system.
 */
@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    /** The repository port for product persistence operations */
    private final ProductRepositoryPort productRepositoryPort;

    /**
     * Fetches a page of products based on the provided filter criteria.
     *
     * @param productFilter The filter criteria for the product search
     * @return A page of products matching the filter criteria
     */
    @Override
    public Page<Product> fetch(ProductFilter productFilter) {
        return productRepositoryPort.findAll(productFilter);
    }

    /**
     * Creates a new product based on the provided request.
     *
     * @param productRequest The request containing product details
     * @return The created product
     */
    @Override
    public Product create(ProductRequest productRequest) {
        return productRepositoryPort.save(productRequest);
    }

    /**
     * Updates an existing product with the specified ID.
     *
     * @param id The UUID of the product to update
     * @param productRequest The request containing updated product details
     * @return The updated product
     */
    @Override
    public Product update(UUID id, ProductRequest productRequest) {
        return productRepositoryPort.update(id, productRequest);
    }

    /**
     * Deletes a product with the specified ID.
     *
     * @param id The UUID of the product to delete
     */
    @Override
    public void delete(UUID id) {
        productRepositoryPort.deleteById(id);
    }
}
