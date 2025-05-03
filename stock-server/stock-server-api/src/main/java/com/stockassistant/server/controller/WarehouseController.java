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

import com.stockassistant.server.api.v1.model.WarehouseModel;
import com.stockassistant.server.api.v1.model.WarehouseRequestModel;
import com.stockassistant.server.api.v1.model.WarehousesModel;
import com.stockassistant.server.api.v1.rest.WarehousesApi;
import com.stockassistant.server.domain.feature.warehouse.port.in.WarehouseUseCase;
import com.stockassistant.server.domain.model.Warehouse;
import com.stockassistant.server.domain.model.WarehouseFilter;
import com.stockassistant.server.mapper.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

/**
 * Controller for handling warehouse-related HTTP requests.
 * This controller implements the WarehousesApi interface and provides endpoints for creating, retrieving, and updating warehouses.
 */
@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class WarehouseController implements WarehousesApi {

    /** The use case for warehouse operations */
    private final WarehouseUseCase warehouseUseCase;

    /** The mapper for converting between domain and API models */
    private final WarehouseMapper warehouseMapper;

    /**
     * Creates a new warehouse based on the provided request model.
     *
     * @param warehouseRequestModel the request model containing warehouse details
     * @return ResponseEntity containing the created warehouse model and a location header
     */
    @Override
    public ResponseEntity<WarehouseModel> createWarehouse(WarehouseRequestModel warehouseRequestModel) {
        log.info("Creating new warehouse with name: {}", warehouseRequestModel.getName());
        Warehouse createdWarehouse = warehouseUseCase.create(warehouseMapper.toWarehouseRequest(warehouseRequestModel));
        log.info("Warehouse created successfully with UUID: {}", createdWarehouse.uuid());
        return ResponseEntity.created(URI.create("/warehouses/" + createdWarehouse.uuid()))
                .body(warehouseMapper.toModel(createdWarehouse));
    }

    /**
     * Retrieves a list of warehouses based on the provided filter criteria.
     *
     * @param page the page number (1-based)
     * @param pageSize the number of items per page
     * @param sort the field to sort by
     * @param uuid the UUID of the warehouse to filter by
     * @param name the name of the warehouse to filter by
     * @param location the location of the warehouse to filter by
     * @param capacity the capacity of the warehouse to filter by
     * @return ResponseEntity containing the list of warehouses
     */
    @Override
    public ResponseEntity<WarehousesModel> getWarehouses(Integer page,
                                                         Integer pageSize,
                                                         String sort,
                                                         UUID uuid,
                                                         String name,
                                                         String location,
                                                         Integer capacity) {
        log.info("Fetching warehouses with filters - page: {}, pageSize: {}, sort: {}, uuid: {}, name: {}, location: {}, capacity: {}",
                page, pageSize, sort, uuid, name, location, capacity);
        return ResponseEntity.ok(
                warehouseMapper.toWarehouses(warehouseUseCase.fetch(
                        WarehouseFilter.builder().pageRequest(PageRequest.of(
                                        page - 1,
                                        pageSize,
                                        Sort.by(sort)))
                                .uuid(uuid)
                                .name(name)
                                .location(location)
                                .capacity(capacity).build()))
        );
    }

    /**
     * Updates an existing warehouse with the specified ID using the provided request model.
     *
     * @param id the UUID of the warehouse to update
     * @param warehouseRequestModel the request model containing updated warehouse details
     * @return ResponseEntity containing the updated warehouse model
     */
    @Override
    public ResponseEntity<WarehouseModel> updateWarehouse(UUID id,
                                                          WarehouseRequestModel warehouseRequestModel) {
        log.info("Updating warehouse with UUID: {}", id);
        WarehouseModel updatedWarehouse = warehouseMapper.toModel(warehouseUseCase.update(
                id,
                warehouseMapper.toWarehouseRequest(warehouseRequestModel)));
        log.info("Warehouse updated successfully");
        return ResponseEntity.ok(updatedWarehouse);
    }
}
