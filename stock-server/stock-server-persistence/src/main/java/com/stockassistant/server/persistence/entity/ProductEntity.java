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

import com.stockassistant.server.persistence.entity.enums.ProductCategoryEnum;
import com.stockassistant.server.persistence.entity.enums.UnitOfMeasureEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity class representing a product in the database.
 * This class maps to the 'product' table and contains all the necessary fields
 * to represent a product in the system.
 */
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductEntity {
    /** The unique identifier of the product in the database */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The universally unique identifier of the product */
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    /** The stock keeping unit (SKU) of the product */
    @Column(nullable = false, unique = true, length = 20)
    private String sku;

    /** The name of the product */
    @Column(nullable = false, length = 100)
    private String name;

    /** The description of the product */
    @Column(length = 500)
    private String description;

    /** The price of the product */
    @Column(nullable = false)
    private Float price;

    /** The category of the product */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProductCategoryEnum category;

    /** The unit of measure for the product */
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure", length = 10)
    private UnitOfMeasureEnum unitOfMeasure;

    /** The timestamp when the product was created */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** The timestamp when the product was last updated */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
