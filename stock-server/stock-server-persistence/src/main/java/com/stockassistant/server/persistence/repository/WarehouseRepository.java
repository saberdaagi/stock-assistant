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

package com.stockassistant.server.persistence.repository;

import com.stockassistant.server.persistence.entity.WarehouseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing warehouse entities in the database.
 * This interface extends JpaRepository to provide CRUD operations and custom queries
 * for warehouse entities.
 */
@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {
    /**
     * Finds a warehouse by its UUID.
     *
     * @param uuid The UUID of the warehouse to find
     * @return An Optional containing the warehouse if found, empty otherwise
     */
    Optional<WarehouseEntity> findByUuid(UUID uuid);

    /**
     * Retrieves all warehouses matching the given specification with pagination.
     *
     * @param specification The criteria for filtering warehouses
     * @param pageable The pagination and sorting information
     * @return A page of warehouses matching the criteria
     */
    Page<WarehouseEntity> findAll(Specification<WarehouseEntity> specification,
                                  Pageable pageable);
}
