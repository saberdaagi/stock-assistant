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
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

/**
 * Represents filter criteria for warehouse queries.
 * This record class contains all possible filter parameters that can be used when searching for warehouses.
 *
 * @param uuid Unique identifier of the warehouse to filter by
 * @param name Name of the warehouse to filter by
 * @param location Location of the warehouse to filter by
 * @param capacity Capacity of the warehouse to filter by
 * @param pageRequest Pagination and sorting information
 */
@Builder(toBuilder = true)
public record WarehouseFilter(
        UUID uuid,
        String name,
        String location,
        Integer capacity,
        PageRequest pageRequest
) {

}