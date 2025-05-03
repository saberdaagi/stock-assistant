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

import com.stockassistant.server.persistence.entity.WarehouseEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/**
 * Utility class providing static methods to create JPA Specifications for WarehouseEntity queries.
 * This class cannot be instantiated as it is a utility class.
 */
public class WarehouseSpecification {
    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @throws IllegalStateException if an attempt is made to instantiate this class
     */
    private WarehouseSpecification() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates a specification to filter warehouses by UUID.
     *
     * @param uuid The UUID to filter by
     * @return A Specification for filtering warehouses by UUID
     */
    public static Specification<WarehouseEntity> hasUUID(UUID uuid) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("uuid"), uuid);
    }

    /**
     * Creates a specification to filter warehouses by name (case-insensitive partial match).
     *
     * @param name The name to filter by
     * @return A Specification for filtering warehouses by name
     */
    public static Specification<WarehouseEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"
        );
    }

    /**
     * Creates a specification to filter warehouses by location (case-insensitive partial match).
     *
     * @param location The location to filter by
     * @return A Specification for filtering warehouses by location
     */
    public static Specification<WarehouseEntity> hasLocation(String location) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("location")), "%" + location.toLowerCase() + "%"
        );
    }

    /**
     * Creates a specification to filter warehouses by capacity.
     *
     * @param capacity The capacity to filter by
     * @return A Specification for filtering warehouses by capacity
     */
    public static Specification<WarehouseEntity> hasCapacity(int capacity) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("capacity"), capacity);
    }
}
