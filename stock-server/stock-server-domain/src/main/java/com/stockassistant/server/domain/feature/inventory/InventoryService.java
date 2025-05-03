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

package com.stockassistant.server.domain.feature.inventory;

import com.stockassistant.server.domain.feature.inventory.port.in.InventoryUseCase;
import com.stockassistant.server.domain.feature.inventory.port.out.InventoryRepositoryPort;
import com.stockassistant.server.domain.model.InventoryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for handling inventory-related business logic.
 * This service implements the InventoryUseCase interface and provides methods for
 * managing inventory items in the system.
 */
@Service
@RequiredArgsConstructor
public class InventoryService implements InventoryUseCase {

    /** The repository port for inventory persistence operations */
    private final InventoryRepositoryPort inventoryRepositoryPort;

    /**
     * Fetches a page of inventory items based on the provided criteria.
     *
     * @param pageRequest The pagination and sorting information
     * @param warehouseId The UUID of the warehouse to filter by
     * @param productId The UUID of the product to filter by
     * @return A page of inventory items matching the criteria
     */
    @Override
    public Page<InventoryItem> fetch(PageRequest pageRequest, UUID warehouseId, UUID productId) {
        return inventoryRepositoryPort.findAll(pageRequest, warehouseId, productId);
    }

    /**
     * Updates the quantity of a specific product in a warehouse.
     *
     * @param warehouseId The UUID of the warehouse
     * @param productId The UUID of the product
     * @param quantity The new quantity of the product
     * @return The updated inventory item
     */
    @Override
    public InventoryItem update(UUID warehouseId, UUID productId, int quantity) {
        return inventoryRepositoryPort.update(warehouseId, productId, quantity);
    }
}
