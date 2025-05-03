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

package com.stockassistant.server.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity class representing an inventory item in the database.
 * This class maps to the 'inventory' table and contains all the necessary fields
 * to represent the stock of a product in a specific warehouse.
 */
@Entity
@Table(name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryItemEntity {
    /** The unique identifier of the inventory item in the database */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The universally unique identifier of the inventory item */
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    /** The current quantity of the product in the warehouse */
    @Column(nullable = false)
    private Integer quantity;

    /** The timestamp of the last stock update */
    @Column(name = "last_stock_update", nullable = false)
    private LocalDateTime lastStockUpdate;

    /** The product associated with this inventory item */
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

    /** The warehouse where this inventory item is stored */
    @ManyToOne(fetch = FetchType.LAZY)
    private WarehouseEntity warehouse;
}
