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

package com.stockassistant.server.persistence.adpater;

import com.stockassistant.server.domain.feature.warehouse.port.out.WarehouseRepositoryPort;
import com.stockassistant.server.domain.model.Warehouse;
import com.stockassistant.server.domain.model.WarehouseFilter;
import com.stockassistant.server.domain.model.WarehouseRequest;
import com.stockassistant.server.persistence.entity.WarehouseEntity;
import com.stockassistant.server.persistence.mapper.WarehouseEntityMapper;
import com.stockassistant.server.persistence.repository.WarehouseRepository;
import com.stockassistant.server.persistence.specification.WarehouseSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Adapter class that implements the WarehouseRepositoryPort interface.
 * This class acts as a bridge between the domain layer and the persistence layer,
 * handling the conversion between domain models and persistence entities.
 */
@Service
@RequiredArgsConstructor
public class WarehouseRepositoryAdapter implements WarehouseRepositoryPort {

    /** The repository for warehouse persistence operations */
    private final WarehouseRepository warehouseRepository;

    /** The mapper for converting between domain models and persistence entities */
    private final WarehouseEntityMapper warehouseEntityMapper;

    /**
     * Retrieves all warehouses matching the given filter criteria.
     *
     * @param warehouseFilter The filter criteria for the warehouse search
     * @return A page of warehouses matching the filter criteria
     */
    @Override
    public Page<Warehouse> findAll(WarehouseFilter warehouseFilter) {
        Specification<WarehouseEntity> specification = Specification.where(null);
        if (Objects.nonNull(warehouseFilter.uuid())) {
            specification = specification.and(WarehouseSpecification.hasUUID(warehouseFilter.uuid()));
        }
        if (Objects.nonNull(warehouseFilter.name())) {
            specification = specification.and(WarehouseSpecification.hasName(warehouseFilter.name()));
        }
        if (Objects.nonNull(warehouseFilter.location())) {
            specification = specification.and(WarehouseSpecification.hasLocation(warehouseFilter.location()));
        }
        if (Objects.nonNull(warehouseFilter.capacity())) {
            specification = specification.and(WarehouseSpecification.hasCapacity(warehouseFilter.capacity()));
        }
        return warehouseRepository.findAll(
                        specification,
                        warehouseFilter.pageRequest())
                .map(warehouseEntityMapper::toWarehouse);
    }

    /**
     * Saves a new warehouse based on the provided request.
     *
     * @param warehouseRequest The request containing warehouse details
     * @return The saved warehouse
     */
    @Override
    public Warehouse save(WarehouseRequest warehouseRequest) {
        WarehouseEntity entity = warehouseEntityMapper.toWarehouseEntity(warehouseRequest);
        return warehouseEntityMapper.toWarehouse(warehouseRepository.save(entity));
    }

    /**
     * Updates an existing warehouse with the specified ID.
     *
     * @param id The UUID of the warehouse to update
     * @param warehouseRequest The request containing updated warehouse details
     * @return The updated warehouse
     * @throws EntityNotFoundException if the warehouse with the given ID is not found
     */
    @Override
    public Warehouse update(UUID id,
                            WarehouseRequest warehouseRequest) {
        return warehouseRepository.findByUuid(id)
                .map(existingEntity -> {
                    existingEntity.setName(warehouseRequest.name());
                    existingEntity.setLocation(warehouseRequest.location());
                    existingEntity.setCapacity(warehouseRequest.capacity());
                    return warehouseEntityMapper.toWarehouse(warehouseRepository.save(existingEntity));
                })
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + id));
    }

    /**
     * Finds a warehouse by its ID.
     *
     * @param id The UUID of the warehouse to find
     * @return An Optional containing the warehouse if found, empty otherwise
     */
    @Override
    public Optional<Warehouse> findById(UUID id) {
        return warehouseRepository.findByUuid(id)
                .map(warehouseEntityMapper::toWarehouse);
    }
}