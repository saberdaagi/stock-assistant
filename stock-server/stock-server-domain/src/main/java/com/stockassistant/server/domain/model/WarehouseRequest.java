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

/**
 * Represents a request to create or update a warehouse.
 * This record class contains the mutable fields of a warehouse that can be set during creation or update.
 *
 * @param name Name of the warehouse
 * @param location Physical location of the warehouse
 * @param capacity Maximum storage capacity of the warehouse
 */
@Builder(toBuilder = true)
public record WarehouseRequest(String name, String location, int capacity) {

}