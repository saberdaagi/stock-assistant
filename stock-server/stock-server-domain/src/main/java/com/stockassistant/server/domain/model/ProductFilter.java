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

import lombok.Builder;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

/**
 * Represents filter criteria for product queries.
 * This record class contains all possible filter parameters that can be used when searching for products.
 *
 * @param pageRequest Pagination and sorting information
 * @param uuid Unique identifier of the product to filter by
 * @param sku Stock Keeping Unit identifier to filter by
 * @param name Name of the product to filter by
 * @param category Category of the product to filter by
 * @param unitOfMeasure Unit of measure to filter by
 * @param price Price of the product to filter by
 */
@Builder(toBuilder = true)
public record ProductFilter(PageRequest pageRequest,
                            UUID uuid,
                            String sku,
                            String name,
                            String category,
                            String unitOfMeasure,
                            Float price
                            ) {
}
