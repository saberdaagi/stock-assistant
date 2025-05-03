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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity class representing a warehouse in the database.
 * This class maps to the 'warehouses' table and contains all the necessary fields
 * to represent a warehouse in the system.
 */
@Entity
@Table(name = "warehouses")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WarehouseEntity {
    /** The unique identifier of the warehouse in the database */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The universally unique identifier of the warehouse */
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    /** The name of the warehouse */
    @Column(nullable = false, length = 100)
    private String name;

    /** The physical location of the warehouse */
    @Column(nullable = false)
    private String location;

    /** The maximum capacity of the warehouse */
    @Column(nullable = false)
    private Integer capacity;

    /** The timestamp when the warehouse was created */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** The timestamp when the warehouse was last updated */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}