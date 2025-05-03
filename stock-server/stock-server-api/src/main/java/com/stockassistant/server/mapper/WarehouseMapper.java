package com.stockassistant.server.mapper;

import com.stockassistant.server.api.v1.model.WarehouseModel;
import com.stockassistant.server.api.v1.model.WarehouseRequestModel;
import com.stockassistant.server.api.v1.model.WarehousesModel;
import com.stockassistant.server.domain.model.Warehouse;
import com.stockassistant.server.domain.model.WarehouseRequest;
import com.stockassistant.server.utils.PageMapperUtils;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    WarehouseModel toModel(Warehouse warehouseModel);

    WarehouseRequest toWarehouseRequest(WarehouseRequestModel warehouseRequestModel);

    default WarehousesModel toWarehouses(Page<Warehouse> page) {
        return PageMapperUtils.toPaginatedModel(
                page,
                this::toModel,
                WarehousesModel::new,
                WarehousesModel::setData,
                WarehousesModel::setTotal,
                WarehousesModel::setPage,
                WarehousesModel::setPageSize
        );
    }
}
