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

package com.stockassistant.server.persistence.mapper;

import com.stockassistant.server.domain.model.Product;
import com.stockassistant.server.domain.model.ProductRequest;
import com.stockassistant.server.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between Product domain models and ProductEntity persistence entities.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface ProductEntityMapper {
    /**
     * Converts a ProductEntity to a Product domain model.
     *
     * @param productEntity The persistence entity to convert
     * @return The converted Product domain model
     */
    Product toProduct(ProductEntity productEntity);

    /**
     * Converts a ProductRequest to a ProductEntity.
     *
     * @param productRequest The request containing product details
     * @return The converted ProductEntity
     */
    ProductEntity mapProductRequestToProductEntity(ProductRequest productRequest);
}
