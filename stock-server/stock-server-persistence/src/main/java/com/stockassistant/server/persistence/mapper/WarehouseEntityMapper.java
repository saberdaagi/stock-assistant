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

import com.stockassistant.server.domain.model.Warehouse;
import com.stockassistant.server.domain.model.WarehouseRequest;
import com.stockassistant.server.persistence.entity.WarehouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between Warehouse domain models and WarehouseEntity persistence entities.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface WarehouseEntityMapper {
    /**
     * Converts a WarehouseEntity to a Warehouse domain model.
     *
     * @param warehouse The persistence entity to convert
     * @return The converted Warehouse domain model
     */
    Warehouse toWarehouse(WarehouseEntity warehouse);

    /**
     * Converts a Warehouse domain model to a WarehouseEntity.
     *
     * @param warehouse The domain model to convert
     * @return The converted WarehouseEntity
     */
    WarehouseEntity toWarehouseEntity(Warehouse warehouse);

    /**
     * Converts a WarehouseRequest to a WarehouseEntity.
     *
     * @param warehouse The request containing warehouse details
     * @return The converted WarehouseEntity
     */
    WarehouseEntity toWarehouseEntity(WarehouseRequest warehouse);
}
