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

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an inventory item in the warehouse.
 * This record class contains information about a product's stock in a specific warehouse,
 * including the quantity and last update timestamp.
 *
 * @param product The product stored in the warehouse
 * @param warehouse The warehouse where the product is stored
 * @param quantity The current quantity of the product in the warehouse
 * @param lastStockUpdate Timestamp of the last stock update
 */
@Builder(toBuilder = true)
public record InventoryItem(
        Product product,
        Warehouse warehouse,
        int quantity,
        LocalDateTime lastStockUpdate
) {
}
