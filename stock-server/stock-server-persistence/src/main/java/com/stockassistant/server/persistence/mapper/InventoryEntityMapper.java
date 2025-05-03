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

import com.stockassistant.server.domain.model.InventoryItem;
import com.stockassistant.server.persistence.entity.InventoryItemEntity;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between InventoryItem domain models and InventoryItemEntity persistence entities.
 * This interface uses MapStruct to generate the implementation at compile time and depends on
 * ProductEntityMapper and WarehouseEntityMapper for nested object mapping.
 */
@Mapper(componentModel = "spring", uses = {ProductEntityMapper.class, WarehouseEntityMapper.class})
public interface InventoryEntityMapper {
    /**
     * Converts an InventoryItemEntity to an InventoryItem domain model.
     *
     * @param item The persistence entity to convert
     * @return The converted InventoryItem domain model
     */
    InventoryItem toInventoryItem(InventoryItemEntity item);

    /**
     * Converts an InventoryItem domain model to an InventoryItemEntity.
     *
     * @param item The domain model to convert
     * @return The converted InventoryItemEntity
     */
    InventoryItemEntity toInventoryItemEntity(InventoryItem item);
}
