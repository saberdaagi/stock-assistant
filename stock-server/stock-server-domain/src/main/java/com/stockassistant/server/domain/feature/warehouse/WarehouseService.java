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

package com.stockassistant.server.domain.feature.warehouse;

import com.stockassistant.server.domain.feature.warehouse.port.in.WarehouseUseCase;
import com.stockassistant.server.domain.feature.warehouse.port.out.WarehouseRepositoryPort;
import com.stockassistant.server.domain.model.Warehouse;
import com.stockassistant.server.domain.model.WarehouseFilter;
import com.stockassistant.server.domain.model.WarehouseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for handling warehouse-related business logic.
 * This service implements the WarehouseUseCase interface and provides methods for
 * managing warehouses in the system.
 */
@Service
@RequiredArgsConstructor
public class WarehouseService implements WarehouseUseCase {
    /** The repository port for warehouse persistence operations */
    private final WarehouseRepositoryPort warehouseRepositoryPort;

    /**
     * Fetches a page of warehouses based on the provided filter criteria.
     *
     * @param warehouseFilter The filter criteria for the warehouse search
     * @return A page of warehouses matching the filter criteria
     */
    @Override
    public Page<Warehouse> fetch(WarehouseFilter warehouseFilter) {
        return warehouseRepositoryPort.findAll(warehouseFilter);
    }

    /**
     * Creates a new warehouse based on the provided request.
     *
     * @param warehouseRequest The request containing warehouse details
     * @return The created warehouse
     */
    @Override
    public Warehouse create(WarehouseRequest warehouseRequest) {
        return warehouseRepositoryPort.save(warehouseRequest);
    }

    /**
     * Updates an existing warehouse with the specified ID.
     *
     * @param id The UUID of the warehouse to update
     * @param warehouseRequest The request containing updated warehouse details
     * @return The updated warehouse
     */
    @Override
    public Warehouse update(UUID id, WarehouseRequest warehouseRequest) {
        return warehouseRepositoryPort.update(id, warehouseRequest);
    }
}
