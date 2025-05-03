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

import com.stockassistant.server.persistence.entity.InventoryItemEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing inventory item entities in the database.
 * This interface extends JpaRepository to provide CRUD operations and custom queries
 * for inventory item entities.
 */
@Repository
public interface InventoryRepository extends JpaRepository<InventoryItemEntity, Long> {
    /**
     * Retrieves all inventory items matching the given specification with pagination.
     *
     * @param specification The criteria for filtering inventory items
     * @param pageable The pagination and sorting information
     * @return A page of inventory items matching the criteria
     */
    Page<InventoryItemEntity> findAll(Specification<InventoryItemEntity> specification, Pageable pageable);

    /**
     * Finds an inventory item by its associated warehouse and product UUIDs.
     *
     * @param warehouseId The UUID of the warehouse
     * @param productId The UUID of the product
     * @return An Optional containing the inventory item if found, empty otherwise
     */
    @Query("""
                SELECT inventoryItemEntity
                FROM InventoryItemEntity inventoryItemEntity
                WHERE inventoryItemEntity.product.uuid = :productId AND inventoryItemEntity.warehouse.uuid = :warehouseId
            """)
    Optional<InventoryItemEntity> findByWarehouseAndProduct(UUID warehouseId, UUID productId);

    /**
     * Updates the quantity of a specific product in a warehouse.
     *
     * @param warehouseId The UUID of the warehouse
     * @param productId The UUID of the product
     * @param quantity The new quantity of the product
     * @return The number of affected rows
     */
    @Modifying
    @Transactional
    @Query("""
                UPDATE InventoryItemEntity inventoryItemEntity
                SET inventoryItemEntity.quantity = :quantity
                WHERE inventoryItemEntity.product.uuid = :productId AND inventoryItemEntity.warehouse.uuid = :warehouseId
            """)
    int updateQuantity(UUID warehouseId, UUID productId, Integer quantity);
}
