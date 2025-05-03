package com.stockassistant.server.persistence.adpater;

import com.stockassistant.server.domain.feature.inventory.port.out.InventoryRepositoryPort;
import com.stockassistant.server.domain.model.InventoryItem;
import com.stockassistant.server.domain.model.excpetion.ObjectNotFoundException;
import com.stockassistant.server.domain.model.excpetion.OperationFailedException;
import com.stockassistant.server.persistence.entity.InventoryItemEntity;
import com.stockassistant.server.persistence.mapper.InventoryEntityMapper;
import com.stockassistant.server.persistence.repository.InventoryRepository;
import com.stockassistant.server.persistence.specification.InventorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryRepositoryAdapter implements InventoryRepositoryPort {
    private final InventoryRepository inventoryRepository;
    private final InventoryEntityMapper inventoryEntityMapper;

    @Override
    public Page<InventoryItem> findAll(Pageable pageable, UUID warehouseUUID, UUID productUUID) {
        Specification<InventoryItemEntity> specification = Specification.where(null);

        if (Objects.nonNull(warehouseUUID)) {
            specification = specification.and(InventorySpecification.hasWarehouseUUID(warehouseUUID));
        }
        if (Objects.nonNull(productUUID)) {
            specification = specification.and(InventorySpecification.hasProductUUID(productUUID));
        }

        return inventoryRepository.findAll(
                        specification,
                        pageable)
                .map(inventoryEntityMapper::toInventoryItem);

    }

    @Override
    public InventoryItem save(InventoryItem item) {
        return inventoryEntityMapper.toInventoryItem(inventoryRepository.save(inventoryEntityMapper.toInventoryItemEntity(item)));
    }

    @Override
    public InventoryItem update(UUID warehouseId, UUID productId, int quantity) {
        int updated = inventoryRepository.updateQuantity(warehouseId, productId, quantity);
        if (updated == 0) {
            throw new OperationFailedException();
        }
        InventoryItemEntity entity = inventoryRepository
                .findByWarehouseAndProduct(warehouseId, productId)
                .orElseThrow(ObjectNotFoundException::new);

        return inventoryEntityMapper.toInventoryItem(entity);
    }

}
