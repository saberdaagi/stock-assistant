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

package com.stockassistant.server.domain.feature.product.port.in;

import com.stockassistant.server.domain.model.Product;
import com.stockassistant.server.domain.model.ProductFilter;
import com.stockassistant.server.domain.model.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

/**
 * Interface defining the use cases for product management.
 * This interface represents the input port for product-related operations in the domain layer.
 */
public interface ProductUseCase {
    /**
     * Fetches a page of products based on the provided filter criteria.
     *
     * @param productFilter The filter criteria for the product search
     * @return A page of products matching the filter criteria
     */
    Page<Product> fetch(ProductFilter productFilter);

    /**
     * Creates a new product based on the provided request.
     *
     * @param input The request containing product details
     * @return The created product
     */
    Product create(ProductRequest input);

    /**
     * Updates an existing product with the specified ID.
     *
     * @param id The UUID of the product to update
     * @param input The request containing updated product details
     * @return The updated product
     */
    Product update(UUID id, ProductRequest input);

    /**
     * Deletes a product with the specified ID.
     *
     * @param id The UUID of the product to delete
     */
    void delete(UUID id);
}
