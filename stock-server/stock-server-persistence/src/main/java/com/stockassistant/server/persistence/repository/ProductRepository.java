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

import com.stockassistant.server.persistence.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing product entities in the database.
 * This interface extends JpaRepository to provide CRUD operations and custom queries
 * for product entities.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * Retrieves all products matching the given specification with pagination.
     *
     * @param specification The criteria for filtering products
     * @param pageable The pagination and sorting information
     * @return A page of products matching the criteria
     */
    Page<ProductEntity> findAll(Specification<ProductEntity> specification,
                                Pageable pageable);

    /**
     * Finds a product by its UUID.
     *
     * @param uuid The UUID of the product to find
     * @return An Optional containing the product if found, empty otherwise
     */
    Optional<ProductEntity> findByUuid(UUID uuid);

    /**
     * Deletes a product by its UUID.
     *
     * @param uuid The UUID of the product to delete
     */
    void deleteByUuid(UUID uuid);
}
