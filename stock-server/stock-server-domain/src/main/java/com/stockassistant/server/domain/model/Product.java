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

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a product in the inventory management system.
 * This record class contains all the essential information about a product including its
 * identification, categorization, pricing, and timestamps.
 *
 * @param uuid Unique identifier for the product
 * @param sku Stock Keeping Unit identifier
 * @param name Name of the product
 * @param description Detailed description of the product
 * @param category Category of the product
 * @param unitOfMeasure Unit of measure for the product
 * @param price Price of the product
 * @param createdAt Timestamp when the product was created
 * @param updatedAt Timestamp when the product was last updated
 */
@Builder(toBuilder = true)
public record Product(
        UUID uuid,
        String sku,
        String name,
        String description,
        ProductCategoryEnum category,
        UnitOfMeasureEnum unitOfMeasure,
        Float price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
