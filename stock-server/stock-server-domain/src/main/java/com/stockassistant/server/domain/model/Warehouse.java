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

package com.stockassistant.server.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a warehouse in the inventory management system.
 * This record class contains all the essential information about a warehouse including its
 * identification, location, capacity, and timestamps.
 *
 * @param uuid Unique identifier for the warehouse
 * @param name Name of the warehouse
 * @param location Physical location of the warehouse
 * @param capacity Maximum storage capacity of the warehouse
 * @param createdAt Timestamp when the warehouse was created
 * @param updatedAt Timestamp when the warehouse was last updated
 */
@Builder(toBuilder = true)
public record Warehouse(
        UUID uuid,
        String name,
        String location,
        int capacity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}