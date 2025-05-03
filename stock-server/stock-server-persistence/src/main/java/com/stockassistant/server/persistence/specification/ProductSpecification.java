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

import com.stockassistant.server.persistence.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/**
 * Utility class providing static methods to create JPA Specifications for ProductEntity queries.
 * This class cannot be instantiated as it is a utility class.
 */
public class ProductSpecification {
    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @throws IllegalStateException if an attempt is made to instantiate this class
     */
    private ProductSpecification() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates a specification to filter products by UUID.
     *
     * @param uuid The UUID to filter by
     * @return A Specification for filtering products by UUID
     */
    public static Specification<ProductEntity> hasUUID(UUID uuid) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("uuid"), uuid);
    }

    /**
     * Creates a specification to filter products by SKU.
     *
     * @param sku The SKU to filter by
     * @return A Specification for filtering products by SKU
     */
    public static Specification<ProductEntity> hasSku(String sku) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sku"), sku);
    }

    /**
     * Creates a specification to filter products by name (case-insensitive partial match).
     *
     * @param name The name to filter by
     * @return A Specification for filtering products by name
     */
    public static Specification<ProductEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"
        );
    }

    /**
     * Creates a specification to filter products by price.
     *
     * @param price The price to filter by
     * @return A Specification for filtering products by price
     */
    public static Specification<ProductEntity> hasPrice(Float price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("price"), price);
    }

    /**
     * Creates a specification to filter products by category.
     *
     * @param category The category to filter by
     * @return A Specification for filtering products by category
     */
    public static Specification<ProductEntity> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }

    /**
     * Creates a specification to filter products by unit of measure.
     *
     * @param unitOfMeasure The unit of measure to filter by
     * @return A Specification for filtering products by unit of measure
     */
    public static Specification<ProductEntity> hasUnitOfMeasure(String unitOfMeasure) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("unitOfMeasure"), unitOfMeasure);
    }
}
