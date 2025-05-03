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

package com.stockassistant.server.persistence.adpater;

import com.stockassistant.server.domain.feature.product.port.out.ProductRepositoryPort;
import com.stockassistant.server.domain.model.Product;
import com.stockassistant.server.domain.model.ProductFilter;
import com.stockassistant.server.domain.model.ProductRequest;
import com.stockassistant.server.domain.model.excpetion.ObjectNotFoundException;
import com.stockassistant.server.persistence.entity.ProductEntity;
import com.stockassistant.server.persistence.mapper.ProductEntityMapper;
import com.stockassistant.server.persistence.repository.ProductRepository;
import com.stockassistant.server.persistence.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * Adapter class that implements the ProductRepositoryPort interface.
 * This class acts as a bridge between the domain layer and the persistence layer,
 * handling the conversion between domain models and persistence entities.
 */
@Service
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {
    /** The repository for product persistence operations */
    private final ProductRepository productRepository;

    /** The mapper for converting between domain models and persistence entities */
    private final ProductEntityMapper productEntityMapper;

    /**
     * Retrieves all products matching the given filter criteria.
     *
     * @param productFilter The filter criteria for the product search
     * @return A page of products matching the filter criteria
     */
    @Override
    public Page<Product> findAll(ProductFilter productFilter) {
        Specification<ProductEntity> specification = Specification.where(null);

        if (Objects.nonNull(productFilter.uuid())) {
            specification = specification.and(ProductSpecification.hasUUID(productFilter.uuid()));
        }
        if (Objects.nonNull(productFilter.name())) {
            specification = specification.and(ProductSpecification.hasName(productFilter.name()));
        }
        if (Objects.nonNull(productFilter.sku())) {
            specification = specification.and(ProductSpecification.hasSku(productFilter.sku()));
        }
        if (Objects.nonNull(productFilter.unitOfMeasure())) {
            specification = specification.and(ProductSpecification.hasUnitOfMeasure(productFilter.unitOfMeasure()));
        }
        if (Objects.nonNull(productFilter.category())) {
            specification = specification.and(ProductSpecification.hasCategory(productFilter.category()));
        }
        if (Objects.nonNull(productFilter.price())) {
            specification = specification.and(ProductSpecification.hasPrice(productFilter.price()));
        }
        return productRepository.findAll(
                        specification,
                        productFilter.pageRequest())
                .map(productEntityMapper::toProduct);
    }

    /**
     * Saves a new product based on the provided request.
     *
     * @param productRequest The request containing product details
     * @return The saved product
     */
    @Override
    public Product save(ProductRequest productRequest) {
        ProductEntity productEntity = productEntityMapper.mapProductRequestToProductEntity(productRequest);
        ProductEntity savedEntity = productRepository.save(productEntity);
        return productEntityMapper.toProduct(savedEntity);
    }

    /**
     * Updates an existing product with the specified ID.
     *
     * @param id The UUID of the product to update
     * @param productRequest The request containing updated product details
     * @return The updated product
     * @throws ObjectNotFoundException if the product with the given ID is not found
     */
    @Override
    public Product update(UUID id,
                          ProductRequest productRequest) {
        return productRepository.findByUuid(id)
                .map(existingProduct -> {
                    ProductEntity productEntity = productEntityMapper.mapProductRequestToProductEntity(productRequest);
                    existingProduct.setName(productEntity.getName());
                    existingProduct.setDescription(productEntity.getDescription());
                    existingProduct.setCategory(productEntity.getCategory());
                    existingProduct.setSku(productEntity.getSku());
                    existingProduct.setUnitOfMeasure(productEntity.getUnitOfMeasure());
                    ProductEntity savedEntity = productRepository.save(existingProduct);
                    return productEntityMapper.toProduct(savedEntity);
                })
                .orElseThrow(ObjectNotFoundException::new);
    }

    /**
     * Deletes a product with the specified ID.
     *
     * @param id The UUID of the product to delete
     */
    @Override
    public void deleteById(UUID id) {
        productRepository.deleteByUuid(id);
    }
}