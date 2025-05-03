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

import com.stockassistant.server.api.v1.model.InventoriesModel;
import com.stockassistant.server.api.v1.model.InventoryItemModel;
import com.stockassistant.server.api.v1.model.UpdateInventoryItemRequestModel;
import com.stockassistant.server.api.v1.rest.InventoryApi;
import com.stockassistant.server.domain.feature.inventory.port.in.InventoryUseCase;
import com.stockassistant.server.domain.model.InventoryItem;
import com.stockassistant.server.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller for handling inventory-related HTTP requests.
 * This controller implements the InventoryApi interface and provides endpoints for retrieving and updating inventory items.
 */
@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class InventoryController implements InventoryApi {

    /** The use case for inventory operations */
    private final InventoryUseCase inventoryUseCase;
    
    /** The mapper for converting between domain and API models */
    private final InventoryMapper inventoryMapper;

    /**
     * Retrieves a list of inventory items based on the provided filter criteria.
     *
     * @param page the page number (1-based)
     * @param pageSize the number of items per page
     * @param warehouseId the UUID of the warehouse to filter by
     * @return ResponseEntity containing the list of inventory items
     */
    @Override
    public ResponseEntity<InventoriesModel> getInventory(Integer page,
                                                         Integer pageSize,
                                                         UUID warehouseId,
                                                         UUID productId
                                                         ) {
        log.info("Fetching inventory items - page: {}, pageSize: {}, warehouseId: {}, productId: {}",
                page, pageSize, warehouseId, productId);
        Page<InventoryItem> inventoryItems = inventoryUseCase.fetch( PageRequest.of(page - 1, pageSize),warehouseId,productId);
        log.info("Found {} inventory items", inventoryItems.getTotalElements());
        return ResponseEntity.ok(inventoryMapper.toInventories(inventoryItems));
    }

    /**
     * Updates an existing inventory item with the specified warehouse and product IDs using the provided request model.
     *
     * @param warehouseId the UUID of the warehouse
     * @param productId the UUID of the product
     * @param updateInventoryItemRequestModel the request model containing updated inventory item details
     * @return ResponseEntity containing the updated inventory item model
     */
    @Override
    public ResponseEntity<InventoryItemModel> updateInventoryItem(UUID warehouseId,
                                                                  UUID productId,
                                                                  UpdateInventoryItemRequestModel updateInventoryItemRequestModel) {
        log.info("Updating inventory item - warehouseId: {}, productId: {}, new quantity: {}",
                warehouseId, productId, updateInventoryItemRequestModel.getQuantity());
        InventoryItem updatedItem = inventoryUseCase.update(warehouseId, productId, updateInventoryItemRequestModel.getQuantity() );
        log.info("Inventory item updated successfully");
        return ResponseEntity.ok(inventoryMapper.toModel(updatedItem));
    }
}
