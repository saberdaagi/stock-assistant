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

package com.stockassistant.server.domain.model;

import com.stockassistant.server.domain.model.enums.ProductCategoryEnum;
import com.stockassistant.server.domain.model.enums.UnitOfMeasureEnum;
import lombok.Builder;

/**
 * Represents a request to create or update a product.
 * This record class contains the mutable fields of a product that can be set during creation or update.
 *
 * @param sku Stock Keeping Unit identifier
 * @param name Name of the product
 * @param description Detailed description of the product
 * @param price Price of the product
 * @param category Category of the product
 * @param unitOfMeasure Unit of measure for the product
 */
@Builder(toBuilder = true)
public record ProductRequest(
        String sku,
        String name,
        String description,
        Float price,
        ProductCategoryEnum category,
        UnitOfMeasureEnum unitOfMeasure
) {
}