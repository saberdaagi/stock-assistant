package com.stockassistant.server.mapper;

import com.stockassistant.server.api.v1.model.InventoriesModel;
import com.stockassistant.server.api.v1.model.InventoryItemModel;
import com.stockassistant.server.domain.model.InventoryItem;
import com.stockassistant.server.utils.PageMapperUtils;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, WarehouseMapper.class})
public interface InventoryMapper {

    InventoryItemModel toModel(InventoryItem inventoryItem);

    default InventoriesModel toInventories(Page<InventoryItem> page) {
        return PageMapperUtils.toPaginatedModel(
                page,
                this::toModel,
                InventoriesModel::new,
                InventoriesModel::setData,
                InventoriesModel::setTotal,
                InventoriesModel::setPage,
                InventoriesModel::setPageSize
        );
    }

}
