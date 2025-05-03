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

package com.stockassistant.server.persistence.specification;

import com.stockassistant.server.persistence.entity.InventoryItemEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/**
 * Utility class providing static methods to create JPA Specifications for InventoryItemEntity queries.
 * This class cannot be instantiated as it is a utility class.
 */
public class InventorySpecification {
    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @throws IllegalStateException if an attempt is made to instantiate this class
     */
    private InventorySpecification() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates a specification to filter inventory items by product UUID.
     *
     * @param productUUID The UUID of the product to filter by
     * @return A Specification for filtering inventory items by product UUID
     */
    public static Specification<InventoryItemEntity> hasProductUUID(UUID productUUID) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("product")
                    .get("uuid"),
                productUUID);
    }

    /**
     * Creates a specification to filter inventory items by warehouse UUID.
     *
     * @param warehouseUUID The UUID of the warehouse to filter by
     * @return A Specification for filtering inventory items by warehouse UUID
     */
    public static Specification<InventoryItemEntity> hasWarehouseUUID(UUID warehouseUUID) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("warehouse")
                    .get("uuid"),
                warehouseUUID);
    }
}
