package com.stockassistant.server.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class PageMapperUtils {

    public static <T, R, M> M toPaginatedModel(
            Page<T> page,
            Function<T, R> itemMapper,
            Supplier<M> modelSupplier,
            BiConsumer<M, List<R>> setData,
            BiConsumer<M, Integer> setTotal,
            BiConsumer<M, Integer> setPage,
            BiConsumer<M, Integer> setPageSize
    ) {
        log.debug("Converting page to paginated model - total elements: {}, page number: {}, page size: {}",
                page.getTotalElements(), page.getNumber(), page.getSize());
        M model = modelSupplier.get();
        List<R> mapped = page.getContent().stream().map(itemMapper).toList();
        setData.accept(model, mapped);
        setTotal.accept(model, (int) page.getTotalElements());
        setPage.accept(model, page.getNumber() + 1);
        setPageSize.accept(model, page.getSize());
        log.debug("Successfully converted page to paginated model");
        return model;
    }
}

