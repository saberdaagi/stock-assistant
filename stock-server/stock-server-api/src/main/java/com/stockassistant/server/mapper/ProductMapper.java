package com.stockassistant.server.mapper;

import com.stockassistant.server.api.v1.model.ProductModel;
import com.stockassistant.server.api.v1.model.ProductRequestModel;
import com.stockassistant.server.api.v1.model.ProductsModel;
import com.stockassistant.server.domain.model.Product;
import com.stockassistant.server.domain.model.ProductRequest;
import com.stockassistant.server.utils.PageMapperUtils;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductRequest toProductRequest(ProductRequestModel product);

    ProductModel toModel(Product product);

    default ProductsModel toProducts(Page<Product> page) {
        return PageMapperUtils.toPaginatedModel(
                page,
                this::toModel,
                ProductsModel::new,
                ProductsModel::setData,
                ProductsModel::setTotal,
                ProductsModel::setPage,
                ProductsModel::setPageSize
        );
    }
}
