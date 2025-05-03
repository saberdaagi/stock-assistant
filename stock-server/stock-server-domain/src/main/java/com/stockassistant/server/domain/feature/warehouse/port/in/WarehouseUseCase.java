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

package com.stockassistant.server.domain.feature.warehouse.port.in;

import com.stockassistant.server.domain.model.Warehouse;
import com.stockassistant.server.domain.model.WarehouseFilter;
import com.stockassistant.server.domain.model.WarehouseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

/**
 * Interface defining the use cases for warehouse management.
 * This interface represents the input port for warehouse-related operations in the domain layer.
 */
public interface WarehouseUseCase {
    /**
     * Fetches a page of warehouses based on the provided filter criteria.
     *
     * @param warehouseFilter The filter criteria for the warehouse search
     * @return A page of warehouses matching the filter criteria
     */
    Page<Warehouse> fetch(WarehouseFilter warehouseFilter);

    /**
     * Creates a new warehouse based on the provided request.
     *
     * @param input The request containing warehouse details
     * @return The created warehouse
     */
    Warehouse create(WarehouseRequest input);

    /**
     * Updates an existing warehouse with the specified ID.
     *
     * @param id The UUID of the warehouse to update
     * @param input The request containing updated warehouse details
     * @return The updated warehouse
     */
    Warehouse update(UUID id, WarehouseRequest input);
}
